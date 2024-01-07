package com.fundy.domain.user.dto.res;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class IsVerifyAccessTokenInfo {
    private boolean isVerify;
    private boolean canRefresh;

    public static IsVerifyAccessTokenInfo of(boolean isVerify, boolean canRefresh) {
        return new IsVerifyAccessTokenInfo(isVerify, canRefresh);
    }

    public boolean isVerify() {
        return isVerify;
    }

    public boolean canRefresh() {
        return canRefresh;
    }
}
