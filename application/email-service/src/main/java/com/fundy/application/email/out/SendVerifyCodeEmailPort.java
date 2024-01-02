package com.fundy.application.email.out;

public interface SendVerifyCodeEmailPort {
    void sendVerifyCode(String email, String code);
}
