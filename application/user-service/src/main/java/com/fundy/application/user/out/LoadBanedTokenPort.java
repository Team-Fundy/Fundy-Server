package com.fundy.application.user.out;

public interface LoadBanedTokenPort {
    boolean existsByAccessToken(String accessToken);
    boolean existsByRefreshToken(String refreshToken);
}