package com.fundy.application.user.in.dto.res;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class IsAvailableNicknameResponse {
    private String targetNickname;
    private boolean isAvailable;

    public static IsAvailableNicknameResponse of(String targetNickname, boolean isAvailable) {
        return new IsAvailableNicknameResponse(targetNickname, isAvailable);
    }
}
