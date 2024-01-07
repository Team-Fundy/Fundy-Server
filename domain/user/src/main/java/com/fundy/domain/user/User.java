package com.fundy.domain.user;

import com.fundy.domain.user.enums.Authority;
import com.fundy.domain.user.interfaces.SecurityUser;
import com.fundy.domain.user.interfaces.TokenizationUser;
import com.fundy.domain.user.vos.CreatorInfo;
import com.fundy.domain.user.vos.Email;
import com.fundy.domain.user.vos.Image;
import com.fundy.domain.user.vos.Password;
import com.fundy.domain.user.vos.Phone;
import com.fundy.domain.user.vos.UserId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class User implements SecurityUser, TokenizationUser {
    private UserId id;
    private Email email;
    private String nickname;
    private Password password;
    private Phone phone;
    private Image profile;
    private List<Authority> authorities = new ArrayList<>();
    private CreatorInfo creatorInfo;
    private List<DeliveryAddress> deliveryAddresses = new ArrayList<>();

    @Builder
    private User(List<Authority> authorities, UserId userId, Email email, String nickname, Password password, Phone phone, Image profile, CreatorInfo creatorInfo, List<DeliveryAddress> deliveryAddresses) {
        this.id = userId;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.phone = phone;
        this.profile = profile;
        this.authorities = authorities;
        this.creatorInfo = creatorInfo;
        this.deliveryAddresses = deliveryAddresses;
    }

    private User(Email email, String nickname, Password password) {
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
        return nickname != null && nickname.length() >= 2 && nickname.length() <= 30;
    }


    @Override
    public String getEmailAddress() {
        return email.getAddress();
    }

    @Override
    public String getEncodedPassword() {
        return password.getEncodedPassword();
    }

    public List<String> getAuthorities() {
        return authorities.stream().map(Authority::name).toList();
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public String getProfileUrl() {
        return profile.toString();
    }
}