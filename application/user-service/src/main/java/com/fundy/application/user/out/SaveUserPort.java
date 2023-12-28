package com.fundy.application.user.out;

import com.fundy.application.user.out.command.SaveUserCommand;

import java.util.UUID;

public interface SaveUserPort {
    UUID saveUser(SaveUserCommand command);
}