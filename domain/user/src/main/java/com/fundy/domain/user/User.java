package com.fundy.domain.user;

import com.fundy.domain.user.enums.Authority;
import com.fundy.domain.user.vos.CreatorInfo;
import com.fundy.domain.user.vos.Email;
import com.fundy.domain.user.vos.Image;
import com.fundy.domain.user.vos.Password;
import com.fundy.domain.user.vos.Phone;
import com.fundy.domain.user.vos.UserId;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class User {
    @Getter
    private UserId id;

    @Getter
    private Email email;

    @Getter
    private String nickname;

    private Password password;

    @Getter
    private Phone phone;

    @Getter
    private Image profile;
    private List<Authority> authorities = new ArrayList<>();

    @Getter
    private CreatorInfo creatorInfo;
    private List<DeliveryAddress> deliveryAddresses = new ArrayList<>();

    private User(Email email, String nickname, Password password) {
        this.id = UserId.newInstance();
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.authorities.addAll(Arrays.asList(Authority.NORMAL, Authority.CREATOR));
    }

    public static User emailSignUp(Email email, String nickname, Password password) {
        if (!validateNickname(nickname))
            throw new IllegalArgumentException("닉네임이 올바르지 않습니다");
        return new User(email, nickname, password);
    }

    public static boolean validateNickname(String nickname) {
        return nickname.length() >= 2 && nickname.length() <= 30 && !nickname.contains("시발");
    }


    public List<String> getAuthorities() {
        return authorities.stream().map(Authority::name).toList();
    }

    public String getCreatorName() {
        return creatorInfo.getName();
    }

    public Image getCreatorBackground() {
        return creatorInfo.getBackground();
    }

    public String getCreatorDescription() {
        return creatorInfo.getDescription();
    }

    public Image getCreatorProfile() {
        return creatorInfo.getProfile();
    }

    public String getPassword() {
        return password.getEncodedPassword();
    }
}