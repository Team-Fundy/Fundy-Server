package com.fundy.domain.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class TokenInfo {
    private final String grantType = "Bearer";
    private String accessToken;
    private String refreshToken;

    private TokenInfo(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static TokenInfo of(String accessToken, String refreshToken) {
        return new TokenInfo(accessToken,refreshToken);
    }
}