package com.fundy.application.user.out;

import com.fundy.application.user.out.dto.req.SaveUserCommand;

import java.util.UUID;

public interface SaveUserPort {
    UUID saveUser(SaveUserCommand command);
}