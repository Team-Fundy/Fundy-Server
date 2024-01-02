package com.fundy.application.email.in.dto.res;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class IsVerifyEmailResponse {
    private String targetEmail;
    private boolean isVerify;

    public static IsVerifyEmailResponse of(String email, boolean isVerify) {
        return new IsVerifyEmailResponse(email, isVerify);
    }
}
