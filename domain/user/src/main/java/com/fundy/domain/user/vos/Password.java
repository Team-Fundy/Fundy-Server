package com.fundy.domain.user.vos;

import lombok.EqualsAndHashCode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EqualsAndHashCode
public class Password {
    private final static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // 대소문자, 숫자, 특수기호(@#$%^&+=!*)를 하나 이상 포함
    private final static String regrex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!*]).*$";
    private final String password;

    private Password(String password) {
        this.password = password;
    }

    public static Password of(String encodedPassword) {
        return new Password(encodedPassword);
    }

    public static Password createEncodedPassword(String password) {
        if (!validatePassword(password))
            throw new IllegalArgumentException("패스워드 정규식 매칭 안됨");
        return new Password(passwordEncoder.encode(password));
    }

    public static boolean validatePassword(String password) {
        return password.matches(regrex) && password.length() >= 10
            && password.length() <= 30;
    }

    public String getEncodedPassword() {
        return password;
    }
}