package com.fundy.application.user.out.dto.req;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UpdateRefreshTokenCommand {
    private String oldRefreshToken;
    private String newRefreshToken;

    public static UpdateRefreshTokenCommand of(String oldRefreshToken, String newRefreshToken) {
        return new UpdateRefreshTokenCommand(oldRefreshToken, newRefreshToken);
    }
}
