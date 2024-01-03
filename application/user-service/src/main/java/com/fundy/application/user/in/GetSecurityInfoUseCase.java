package com.fundy.application.user.in;

import com.fundy.application.user.in.dto.res.SecurityInfoResponse;

public interface GetSecurityInfoUseCase {
    SecurityInfoResponse getSecurityInfoByEmail(String email);
}
