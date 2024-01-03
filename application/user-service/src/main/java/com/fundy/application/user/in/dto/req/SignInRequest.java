package com.fundy.application.user.in.dto.req;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SignInRequest {
    private String email;
    private List<String> authorities;

    public static SignInRequest of(String email, List<String> authorities) {
        return new SignInRequest(email, authorities);
    }
}