package com.fundy.domain.email;

import com.fundy.domain.email.dto.req.VerifyTokenRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.Optional;
import java.util.Properties;

public class EmailTokenProvider {
    public static final EmailTokenProvider INSTANCE = new EmailTokenProvider();
    private final static String CLAIM_NAME = "code";
    private static Key key;

    private EmailTokenProvider() {
        try {
            Properties properties = new Properties();
            properties.load(getClass()
                .getClassLoader()
                .getResourceAsStream("email-token.properties"));

            key = Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(properties.getProperty("email-token.key")));
        } catch (IOException e) {
            throw new RuntimeException("email token property exception");
        }
    }

    public String generateToken(String email, String code) {
        Date now = new Date();
        long JWT_DURATION = 5 * 60 * 1000L; // 3분
        Date expirationDate = new Date(now.getTime() + JWT_DURATION);

        return Jwts.builder()
            .setSubject(email)
            .claim(CLAIM_NAME, code)
            .setIssuedAt(now)
            .setExpiration(expirationDate)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    public boolean isVerifyToken(VerifyTokenRequest request) {
        return getClaims(request.getToken())
            .map((claims) -> {
                String email = claims.getSubject();
                String code = claims.get(CLAIM_NAME).toString();

                 return request.getEmail().equals(email) && request.getCode().equals(code);
            })
            .orElse(false);
    }

    private Optional<Claims> getClaims(String token) {
        try {
            return Optional.ofNullable(Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody());
        } catch (ExpiredJwtException e ) {
            return Optional.ofNullable(e.getClaims()); // Expired된 Jwt 던짐
        } catch (JwtException e) {
            return Optional.empty();
        }
    }
}
