package com.fundy.api.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "토큰 리프레쉬가 가능한지")
public class GlobalCanRefreshResponse {
    private boolean success = false;
    private String message;
    private boolean canRefresh;

    @Builder
    private GlobalCanRefreshResponse(String message, boolean canRefresh) {
        this.message = message;
        this.canRefresh = canRefresh;
    }
}