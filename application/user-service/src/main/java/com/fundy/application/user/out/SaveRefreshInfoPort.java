package com.fundy.application.user.out;

import com.fundy.application.user.out.command.SaveRefreshInfoCommand;

public interface SaveRefreshInfoPort {
    void save(final SaveRefreshInfoCommand command);
}
