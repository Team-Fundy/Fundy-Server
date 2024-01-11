package com.fundy.domain.user;

import com.fundy.domain.user.enums.Authority;
import com.fundy.domain.user.interfaces.TokenizationUser;
import com.fundy.domain.user.vos.Email;
import com.fundy.domain.user.vos.Image;
import com.fundy.domain.user.vos.Nickname;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@DisplayName("유저 토큰 생성 유닛 테스트")
class UserTokenProviderTest {

    @DisplayName("[성공] 토큰 생성")
    @Test
    void generateToken() {
        // given
        Email email = Email.of("dongwon0103@naver.com");
        List<Authority> authorities = new ArrayList<>(Arrays.asList(Authority.NORMAL, Authority.CREATOR));

        // when
        TokenInfo tokenInfo = UserTokenProvider.INSTANCE.generateToken(User.builder()
                .email(email)
                .authorities(authorities)
                .nickname(Nickname.of("nickname"))
                .profile(Image.of("http://www.naver.com/image"))
            .build());

        // then
        System.out.println(tokenInfo.getAccessToken());
        Assertions.assertThat(tokenInfo.getAccessToken()).isNotEmpty();
        Assertions.assertThat(tokenInfo.getRefreshToken()).isNotEmpty();
        Assertions.assertThat(tokenInfo.getGrantType()).isNotEmpty();
    }

    @DisplayName("[성공] 액세스 토큰 검증")
    @Test
    void validateToken() {
        // given
        TokenizationUser user = User.builder()
            .email(Email.of("dongwon010@naver.com"))
            .authorities(new ArrayList<>(Arrays.asList(Authority.NORMAL, Authority.CREATOR)))
            .nickname(Nickname.of("nickname"))
            .profile(Image.of("http://www.naver.com/image"))
            .build();

        TokenInfo tokenInfo = UserTokenProvider.INSTANCE.generateToken(user);

        // when
        boolean response = UserTokenProvider.INSTANCE.isVerifyAccessToken(tokenInfo.getAccessToken());

        // then
        Assertions.assertThat(response).isTrue();
    }

    @DisplayName("[실패] 액세스 토큰 검증: 옳지 못한 토큰")
    @Test
    void verifyAccessTokenFailCaseWithInvalidToken() {
        // given
        String invalidToken = "adfjakldfjkalfdjkald";

        // when
        boolean response = UserTokenProvider.INSTANCE.isVerifyAccessToken(invalidToken);

        // then
        Assertions.assertThat(response).isFalse();
    }

    @DisplayName("[실패] 액세스 토큰 검증: 만료")
    @Test
    void validateTokenFailWithExpired() {
        // given
        TokenizationUser user = User.builder()
            .email(Email.of("dongwon010@naver.com"))
            .authorities(new ArrayList<>(Arrays.asList(Authority.NORMAL, Authority.CREATOR)))
            .nickname(Nickname.of("nickname"))
            .profile(Image.of("http://www.naver.com/image"))
            .build();

        TokenInfo tokenInfo = UserTokenProvider.INSTANCE.generateToken(user, LocalDateTime.now().minusHours(5));

        // when
        boolean response = UserTokenProvider.INSTANCE.isVerifyAccessToken(tokenInfo.getAccessToken());

        // then
        Assertions.assertThat(response).isFalse();
    }

    @DisplayName("[성공] 액세스 토큰 리프레쉬 가능 여부")
    @Test
    void refreshAccessToken() {
        // given
        TokenInfo tokenInfo = UserTokenProvider.INSTANCE.generateToken(User.builder()
                .email(Email.of("do01@naver.com"))
                .nickname(Nickname.of("nic"))
                .authorities(Collections.singletonList(Authority.NORMAL))
                .profile(Image.of("http://www.naver.com"))
            .build());

        // when
        boolean result = UserTokenProvider.INSTANCE.canRefresh(tokenInfo.getAccessToken());

        // then
        Assertions.assertThat(result).isTrue();
    }

    @DisplayName("[성공] 액세스 토큰 리프레쉬 가능 여부: 만료됨")
    @Test
    void refreshAccessTokenWithExpired() {
        // given
        TokenInfo tokenInfo = UserTokenProvider.INSTANCE.generateToken(User.builder()
            .email(Email.of("do01@naver.com"))
            .nickname(Nickname.of("nic"))
            .authorities(Collections.singletonList(Authority.NORMAL))
            .profile(Image.of("http://www.naver.com"))
            .build(), LocalDateTime.now().minusHours(5));

        // when
        boolean result = UserTokenProvider.INSTANCE.canRefresh(tokenInfo.getAccessToken());

        // then
        Assertions.assertThat(result).isTrue();
    }

    @DisplayName("[성공] 액세스 토큰 리프레쉬: 옳지 못한 토큰")
    @Test
    void refreshAccessTokenFailWithInvalidToken() {
        // given
        String invalidToken = "Invalid Token";

        // when
        boolean result = UserTokenProvider.INSTANCE.canRefresh(invalidToken);

        // then
        Assertions.assertThat(result).isFalse();
    }

    @DisplayName("[성공] 리프레쉬 토큰 검증")
    @Test
    void refreshTokenValidate() {
        // given
        TokenInfo tokenInfo = UserTokenProvider.INSTANCE.generateToken(User.builder()
            .email(Email.of("do01@naver.com"))
            .nickname(Nickname.of("nic"))
            .authorities(Collections.singletonList(Authority.NORMAL))
            .profile(Image.of("http://www.naver.com"))
            .build());

        // when
        boolean result = UserTokenProvider.INSTANCE.isVerifyRefreshToken(tokenInfo.getRefreshToken());

        // then
        Assertions.assertThat(result).isTrue();
    }

    @DisplayName("[성공] 리프레쉬 토큰 검증")
    @Test
    void refreshTokenValidateFailWithInvalidToken() {
        // given
        String invalidToken = "invalid";

        // when
        boolean result = UserTokenProvider.INSTANCE.isVerifyRefreshToken(invalidToken);

        // then
        Assertions.assertThat(result).isFalse();
    }
}