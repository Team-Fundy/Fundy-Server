package com.fundy.application.email;

import com.fundy.application.email.in.dto.res.SendVerifyCodeResponse;
import com.fundy.application.email.out.SendVerifyCodeEmailPort;
import com.fundy.application.user.in.IsAvailableEmailUseCase;
import com.fundy.application.user.in.dto.res.IsAvailableEmailResponse;
import com.fundy.domain.email.EmailTokenProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("이메일 인증 테스트")
class EmailAuthServiceTest {
    @InjectMocks
    private EmailAuthService emailAuthService;
    @Spy
    private EmailTokenProvider emailTokenProvider;
    @Mock
    private SendVerifyCodeEmailPort sendVerifyCodeEmailPort;
    @Mock
    private IsAvailableEmailUseCase isAvailableEmailUseCase;

    @DisplayName("[성공] 이메일 인증 메일 보내기")
    @Test
    void sendVerifyCode() {
        // given
        String email = "test01@naver.com";
        given(isAvailableEmailUseCase.isAvailableEmail(email)).willReturn(IsAvailableEmailResponse.of(email, true));

        // when
        SendVerifyCodeResponse response = emailAuthService.sendVerifyCode(email);

        // then
        Assertions.assertThat(response.getEmail()).isEqualTo(email);
        Assertions.assertThat(response.getToken()).isNotEmpty();
        verify(isAvailableEmailUseCase, times(1)).isAvailableEmail(anyString());
        verify(sendVerifyCodeEmailPort, times(1)).sendVerifyCode(anyString(), anyString());
    }
}