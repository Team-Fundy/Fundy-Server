package com.fundy.domain.user;

import com.fundy.domain.user.dto.res.IsVerifyAccessTokenInfo;
import com.fundy.domain.user.dto.res.TokenInfo;
import com.fundy.domain.user.enums.Authority;
import com.fundy.domain.user.interfaces.TokenizationUser;
import com.fundy.domain.user.vos.Email;
import com.fundy.domain.user.vos.Image;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Slf4j
public class UserTokenProvider {
    public static final UserTokenProvider INSTANCE = new UserTokenProvider();
    private static Key accessKey;
    private static Key refreshKey;
    private final String AUTH_CLAIM_NAME = "authorities";
    private final String NICKNAME_CLAIM_NAME = "nickname";
    private final String PROFILE_CLAIM_NAME = "profile";
    @Getter
    private final String grantType = "Bearer";

    private UserTokenProvider() {
        try {
            Properties properties = new Properties();
            properties.load(getClass()
                .getClassLoader()
                .getResourceAsStream("user-token.properties"));

            accessKey = decodeKey(properties, "user-token.access.key");
            refreshKey = decodeKey(properties, "user-token.refresh.key");
        } catch (IOException e) {
            throw new RuntimeException("user token property exception");
        }
    }
    private Key decodeKey(Properties properties, String propertyKey) {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(properties.getProperty(propertyKey)));
    }

    public TokenInfo generateToken(final TokenizationUser tokenizationUser) {
        final long ACCESS_DURATION = 2 * 60 * 60 * 1000L; // 2시간
        final long REFRESH_DURATION = 30 * 24 * 60 * 60 * 1000L; // 30일

        Date now = new Date();
        Date AccessExpirationDate = new Date(now.getTime() + ACCESS_DURATION);
        Date RefreshExpirationDate = new Date(now.getTime() + REFRESH_DURATION);

        String accessToken = Jwts.builder()
            .setSubject(tokenizationUser.getEmailAddress())
            .claim(AUTH_CLAIM_NAME, tokenizationUser.getAuthorities())
            .claim(NICKNAME_CLAIM_NAME, tokenizationUser.getNickname())
            .claim(PROFILE_CLAIM_NAME, tokenizationUser.getProfileUrl())
            .setIssuedAt(now)
            .setExpiration(AccessExpirationDate)
            .signWith(accessKey, SignatureAlgorithm.HS256)
            .compact();

        String refreshToken = Jwts.builder()
            .setExpiration(RefreshExpirationDate)
            .signWith(refreshKey, SignatureAlgorithm.HS256)
            .compact();

        return TokenInfo.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .grantType(grantType)
            .build();
    }

    public Optional<TokenizationUser> getTokenizationUserInfo(String accessToken) {
        Claims claims = getClaims(accessKey, accessToken);
        if (claims == null)
            return Optional.empty();

        List<String> authorities = (List<String>) claims.get(AUTH_CLAIM_NAME);

        return Optional.ofNullable(User.builder()
                .email(Email.of(claims.getSubject()))
                .authorities(authorities.stream().map(Authority::valueOf).toList())
                .nickname((String) claims.get(NICKNAME_CLAIM_NAME))
                .profile(Image.of((String) claims.get(PROFILE_CLAIM_NAME)))
            .build());
    }

    private Claims getClaims(Key key, String token) {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }

    public IsVerifyAccessTokenInfo isVerifyAccessToken(String accessToken) {
        try {
            if (isVerifyToken(accessKey, accessToken))
                return IsVerifyAccessTokenInfo.of(true, true);

            return IsVerifyAccessTokenInfo.of(false, false);
        } catch (ExpiredJwtException e) {
            return IsVerifyAccessTokenInfo.of(false, true);
        }
    }

    private boolean isVerifyToken(Key key, String token) throws ExpiredJwtException {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            throw e;
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }

        return false;
    }
}