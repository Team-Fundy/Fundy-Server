package com.fundy.domain.user;

import com.fundy.domain.user.enums.Authority;
import com.fundy.domain.user.vos.Email;
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
        TokenInfo tokenInfo = UserTokenProvider.INSTANCE.generateToken(email, authorities);

        // then
        System.out.println(tokenInfo.getAccessToken());
        Assertions.assertThat(tokenInfo.getAccessToken()).isNotEmpty();
        Assertions.assertThat(tokenInfo.getRefreshToken()).isNotEmpty();
        Assertions.assertThat(tokenInfo.getGrantType()).isNotEmpty();
    }
}