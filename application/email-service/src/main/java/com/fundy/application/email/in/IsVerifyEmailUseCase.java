package com.fundy.application.email.in;

import com.fundy.application.email.in.dto.req.IsVerifyEmailRequest;
import com.fundy.application.email.in.dto.res.IsVerifyEmailResponse;

public interface IsVerifyEmailUseCase {
    IsVerifyEmailResponse isVerifyEmail(final IsVerifyEmailRequest request);
}
