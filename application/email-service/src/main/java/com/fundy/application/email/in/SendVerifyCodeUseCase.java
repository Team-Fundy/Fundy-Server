package com.fundy.application.email.in;

import com.fundy.application.email.in.dto.res.SendVerifyCodeResponse;

public interface SendVerifyCodeUseCase {
    SendVerifyCodeResponse sendVerifyCode(String email);
}
