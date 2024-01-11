package com.fundy.application.user.in;

public interface CanTokenRefreshUseCase {
    boolean canRefresh(String accessToken);
}
