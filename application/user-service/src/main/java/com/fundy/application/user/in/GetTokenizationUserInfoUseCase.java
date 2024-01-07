package com.fundy.application.user.in;

import com.fundy.application.user.in.dto.res.TokenizationUserInfoResponse;

public interface GetTokenizationUserInfoUseCase {
    TokenizationUserInfoResponse getTokenizationUserInfoByAccessToken(String accessToken);
}
