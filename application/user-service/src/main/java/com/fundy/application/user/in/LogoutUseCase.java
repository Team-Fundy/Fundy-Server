package com.fundy.application.user.in;

import com.fundy.application.user.in.dto.req.LogoutRequest;

public interface LogoutUseCase {
    void logout(final LogoutRequest request);
}
