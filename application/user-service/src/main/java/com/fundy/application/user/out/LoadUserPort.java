package com.fundy.application.user.out;

import com.fundy.domain.user.User;

import java.util.Optional;

public interface LoadUserPort {
    Optional<User> findByEmail(String email);
    Optional<User> findByNickname(String nickname);
}