package com.fundy.application.user.out;

import com.fundy.application.user.out.command.SaveUserCommand;
import com.fundy.domain.user.User;

public interface SaveUserPort {
    User saveUser(SaveUserCommand command);
}