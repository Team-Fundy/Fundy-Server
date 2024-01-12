package com.fundy.application.user.out;

import com.fundy.application.user.out.dto.req.SaveBanedTokenCommand;

public interface SaveBanedTokenPort {
    void save(final SaveBanedTokenCommand command);
}
