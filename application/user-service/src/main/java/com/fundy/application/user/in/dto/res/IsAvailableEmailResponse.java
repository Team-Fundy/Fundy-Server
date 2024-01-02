package com.fundy.application.user.in.dto.res;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class IsAvailableEmailResponse {
    private String targetEmail;
    private boolean isAvailable;

    public static IsAvailableEmailResponse of(String targetEmail, boolean isAvailable) {
        return new IsAvailableEmailResponse(targetEmail, isAvailable);
    }
}
