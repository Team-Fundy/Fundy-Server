package com.fundy.application.user.in.dto.req;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GenerateToeknRequest {
    private String email;
    private List<String> authorities;

    public static GenerateToeknRequest of(String email, List<String> authorities) {
        return new GenerateToeknRequest(email, authorities);
    }
}