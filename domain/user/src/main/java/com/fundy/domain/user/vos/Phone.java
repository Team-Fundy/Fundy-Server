package com.fundy.domain.user.vos;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Phone {
    private String number;

    public static Phone of(String number) {
        if (number == null)
            throw new IllegalArgumentException("전화번호가 존재하지 않습니다");

        if (!isValidateNumber(number))
            throw new IllegalArgumentException("휴대폰 번호 형식과 맞지 않습니다");
        return new Phone(number);
    }

    public static boolean isValidateNumber(String phone) {
        for(Character c:phone.toCharArray()) {
            if (!Character.isDigit(c))
                return false;
        }
        return phone.length() == 9;
    }

    public String getNumberWithHyphen() {
        StringBuilder sb = new StringBuilder(number);
        sb.insert(3,"-");
        sb.insert(8,"-");

        return sb.toString();
    }
}