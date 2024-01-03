package com.fundy.application.user.in;

import com.fundy.application.user.in.dto.req.SignInRequest;
import com.fundy.application.user.in.dto.res.SignInResponse;

public interface SignInUseCase {
    SignInResponse signIn(final SignInRequest request);
}
