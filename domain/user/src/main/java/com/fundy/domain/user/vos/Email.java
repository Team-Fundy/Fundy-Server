package com.fundy.domain.user.vos;

public class Email {
    private String address;
    private final static String pattern = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";

    private Email(String address) {
        this.address = address;
    }

    public static Email of(String address) {
        if(!isEmailFormat(address))
            throw new IllegalArgumentException("이메일 표현식이 올바르지 않습니다");
        return new Email(address);
    }

    public static boolean isEmailFormat(String email) {
        return email.matches(pattern);
    }

    public String getAddress() {
        return address;
    }
}