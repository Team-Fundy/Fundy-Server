package com.fundy.domain.user;

import com.fundy.domain.user.dto.res.IsVerifyAccessTokenInfo;
import com.fundy.domain.user.dto.res.TokenInfo;
import com.fundy.domain.user.enums.Authority;
import com.fundy.domain.user.interfaces.TokenizationUser;
import com.fundy.domain.user.vos.Email;
import com.fundy.domain.user.vos.Image;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
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
                .nickname("nickname")
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
            .nickname("nickname")
            .profile(Image.of("http://www.naver.com/image"))
            .build();

        TokenInfo tokenInfo = UserTokenProvider.INSTANCE.generateToken(user);

        // when
        IsVerifyAccessTokenInfo response = UserTokenProvider.INSTANCE.isVerifyAccessToken(tokenInfo.getAccessToken());

        // then
        Assertions.assertThat(response.isVerify()).isTrue();
        Assertions.assertThat(response.canRefresh()).isTrue();
    }

    @DisplayName("[실패] 액세스 토큰 검증: 옳지 못한 토큰")
    @Test
    void verifyAccessTokenFailCaseWithInvalidToken() {
        // given
        String invalidToken = "adfjakldfjkalfdjkald";

        // when
        IsVerifyAccessTokenInfo response = UserTokenProvider.INSTANCE.isVerifyAccessToken(invalidToken);

        // then
        Assertions.assertThat(response.isVerify()).isFalse();
        Assertions.assertThat(response.canRefresh()).isFalse();
    }
}