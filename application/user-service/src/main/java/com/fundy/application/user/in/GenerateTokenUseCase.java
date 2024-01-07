package com.fundy.application.user.in;

import com.fundy.application.user.in.dto.req.GenerateToeknRequest;
import com.fundy.application.user.in.dto.res.TokenInfoResponse;

public interface GenerateTokenUseCase {
    TokenInfoResponse generateToken(final GenerateToeknRequest request);
}
