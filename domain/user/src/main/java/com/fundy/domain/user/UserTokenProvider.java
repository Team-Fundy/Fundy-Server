package com.fundy.domain.user;

import com.fundy.domain.user.enums.Authority;
import com.fundy.domain.user.vos.Email;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class UserTokenProvider {
    public static final UserTokenProvider INSTANCE = new UserTokenProvider();
    private static Key accessKey;
    private static Key refreshKey;
    private final String AUTH_CLAIM_NAME = "authorities";

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

    public TokenInfo generateToken(Email email, List<Authority> authorities) {
        final long ACCESS_DURATION = 2 * 60 * 60 * 1000L; // 2시간
        final long REFRESH_DURATION = 30 * 24 * 60 * 60 * 1000L; // 30일

        Date now = new Date();
        Date AccessExpirationDate = new Date(now.getTime() + ACCESS_DURATION);
        Date RefreshExpirationDate = new Date(now.getTime() + REFRESH_DURATION);

        String accessToken = Jwts.builder()
            .setSubject(email.getAddress())
            .claim(AUTH_CLAIM_NAME, authorities.stream().map(Authority::name).toList())
            .setIssuedAt(now)
            .setExpiration(AccessExpirationDate)
            .signWith(accessKey, SignatureAlgorithm.HS256)
            .compact();

        String refreshToken = Jwts.builder()
            .setExpiration(RefreshExpirationDate)
            .signWith(refreshKey, SignatureAlgorithm.HS256)
            .compact();

        return TokenInfo.of(accessToken, refreshToken);
    }
}