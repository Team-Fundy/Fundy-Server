package com.fundy.domain.user.vos;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Nickname {
    private String name;

    public static Nickname of(String name) {
        if (!validateNickname(name))
            throw new IllegalArgumentException("닉네임이 올바르지 않습니다");
        return new Nickname(name);
    }

    public static boolean validateNickname(String nickname) {
        return nickname != null && nickname.length() >= 2 && nickname.length() <= 30;
    }

    @Override
    public String toString() {
        return name;
    }
}
