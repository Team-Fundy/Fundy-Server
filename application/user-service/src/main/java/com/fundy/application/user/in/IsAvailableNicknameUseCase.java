package com.fundy.application.user.in;

import com.fundy.application.user.in.dto.res.IsAvailableNicknameResponse;

public interface IsAvailableNicknameUseCase {
    IsAvailableNicknameResponse isAvailableNickname(String nickname);
}
