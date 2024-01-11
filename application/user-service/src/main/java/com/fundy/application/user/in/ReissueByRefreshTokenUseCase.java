package com.fundy.application.user.in;

import com.fundy.application.user.in.dto.res.TokenInfoResponse;

public interface ReissueByRefreshTokenUseCase {
    TokenInfoResponse reissue(String refreshToken);
}
