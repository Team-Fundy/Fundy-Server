package com.fundy.application.user.in;

import com.fundy.application.user.in.dto.req.SignUpRequest;
import com.fundy.application.user.in.dto.res.SignUpResponse;

public interface SignUpUseCase {
    SignUpResponse signUp(final SignUpRequest signUpRequest);
}
