package com.fundy.application.user.out;

public interface ValidUserPort {
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);
}
