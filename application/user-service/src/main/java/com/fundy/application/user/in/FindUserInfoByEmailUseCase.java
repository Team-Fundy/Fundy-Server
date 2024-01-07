package com.fundy.application.user.in;

import com.fundy.application.user.in.dto.res.UserInfoResponse;

public interface FindUserInfoByEmailUseCase {
    UserInfoResponse findByEmail(String email);
}
