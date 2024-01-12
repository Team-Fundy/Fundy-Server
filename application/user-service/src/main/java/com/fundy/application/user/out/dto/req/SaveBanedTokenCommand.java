package com.fundy.application.user.out.dto.req;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SaveBanedTokenCommand {
    private String accessToken;
    private String refreshToken;

    public static SaveBanedTokenCommand of(String accessToken, String refreshToken) {
        return new SaveBanedTokenCommand(accessToken, refreshToken);
    }
}