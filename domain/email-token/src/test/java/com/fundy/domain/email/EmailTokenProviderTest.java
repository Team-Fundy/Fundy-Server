package com.fundy.domain.email;

import com.fundy.domain.email.dto.req.VerifyTokenRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("이메일 토큰 생성 유닛 테스트")
class EmailTokenProviderTest {

    @DisplayName("[성공] 토큰 생성")
    @Test
    void generateToken() {
        // given
        String email = "dongwon0103@naver.com";
        String code = "59249A";

        // when
        String token = EmailTokenProvider.INSTANCE.generateToken(email,code);

        // then
        System.out.println(token);
        Assertions.assertThat(token).isNotEmpty();
    }

    @DisplayName("[성공] 토큰 검증")
    @Test
    void verifyToken() {
        // given
        String email = "dongwon0103@naver.com";
        String code = "59249A";
        String token = EmailTokenProvider.INSTANCE.generateToken(email,code);

        // when
        boolean result = EmailTokenProvider.INSTANCE.isVerifyToken(VerifyTokenRequest.builder()
                .code(code)
                .token(token)
                .email(email)
            .build());

        // then
        Assertions.assertThat(result).isTrue();
    }

    @DisplayName("[실패] 토큰 검증: 토큰이 옳지 못함")
    @Test
    void verifyTokenFailCaseWithNotToken() {
        // given
        String email = "dongwon0103@naver.com";
        String code = "59249A";
        String token = "is dumb";

        // when
        boolean result = EmailTokenProvider.INSTANCE.isVerifyToken(VerifyTokenRequest.builder()
            .code(code)
            .token(token)
            .email(email)
            .build());

        // then
        Assertions.assertThat(result).isFalse();
    }
}