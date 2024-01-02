package com.fundy.application.email.in.dto.res;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SendVerifyCodeResponse {
    private String email;
    private String token;

    public static SendVerifyCodeResponse of(String email, String token) {
        return new SendVerifyCodeResponse(email, token);
    }
}
