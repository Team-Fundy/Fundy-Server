package com.fundy.application.user.out.dto.req;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class SaveRefreshInfoCommand {
    private String email;
    private List<String> authorities;
    private String nickname;
    private String profile;
    private String refreshToken;
}
