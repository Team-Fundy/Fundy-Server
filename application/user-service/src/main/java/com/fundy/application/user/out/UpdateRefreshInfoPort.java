package com.fundy.application.user.out;

import com.fundy.application.user.out.dto.req.UpdateRefreshTokenCommand;

public interface UpdateRefreshInfoPort {
    void updateRefreshToken(UpdateRefreshTokenCommand command);
}
