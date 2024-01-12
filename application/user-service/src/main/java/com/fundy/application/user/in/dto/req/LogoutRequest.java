package com.fundy.application.user.in.dto.req;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LogoutRequest {
    private String accessToken;
    private String refreshToken;

    public static LogoutRequest of(String accessToken, String refreshToken) {
        return new LogoutRequest(accessToken, refreshToken);
    }
}