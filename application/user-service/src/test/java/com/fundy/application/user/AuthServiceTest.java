package com.fundy.application.user;

import com.fundy.application.exception.custom.DuplicateInstanceException;
import com.fundy.application.exception.custom.ValidationException;
import com.fundy.application.user.in.dto.req.SignUpRequest;
import com.fundy.application.user.in.dto.res.SignUpResponse;
import com.fundy.application.user.out.SaveUserPort;
import com.fundy.application.user.out.ValidUserPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Auth Service 유닛 테스트")
class AuthServiceTest {
    @InjectMocks
    private AuthService authService;
    @Mock
    private SaveUserPort saveUserPort;
    @Mock
    private ValidUserPort validUserPort;

    @DisplayName("[성공] 회원가입")
    @Test
    void signUpSuccessCase() {
        // given
        SignUpRequest request = SignUpRequest.builder()
            .email("dong0102@naver.com")
            .password("@fjfdjafjlk!fJ12")
            .nickname("펀디유저")
            .build();

        given(validUserPort.existsByEmail(request.getEmail())).willReturn(false);
        given(validUserPort.existsByNickname(request.getNickname())).willReturn(false);
        given(saveUserPort.saveUser(any())).willReturn(UUID.randomUUID());

        // when
        SignUpResponse response = authService.signUp(request);

        // then
        Assertions.assertThat(response.getEmail()).isEqualTo(request.getEmail());
        Assertions.assertThat(response.getNickname()).isEqualTo(request.getNickname());
        verify(validUserPort, times(1)).existsByEmail(any());
        verify(validUserPort, times(1)).existsByNickname(any());
        verify(saveUserPort, times(1)).saveUser(any());
    }

    @DisplayName("[실패] 회원가입: 이메일 포맷")
    @Test
    void signUpFailCaseWithEmailFormat() {
        // given
        SignUpRequest request = SignUpRequest.builder()
            .email("donger.com")
            .password("@fjfdjafjlk!fJ12")
            .nickname("펀디유저")
            .build();

        // when, then
        Assertions.assertThatThrownBy(() -> authService.signUp(request)).isInstanceOf(ValidationException.class);
        verify(saveUserPort, times(0)).saveUser(any());
    }

    @DisplayName("[실패] 회원가입: 닉네임 중복")
    @Test
    void signUpFailCaseWithDuplicateNickname() {
        // given
        SignUpRequest request = SignUpRequest.builder()
            .email("donger0103@naver.com")
            .password("@fjfdjafjlk!fJ12")
            .nickname("펀디유저")
            .build();

        given(validUserPort.existsByEmail(request.getEmail())).willReturn(false);
        given(validUserPort.existsByNickname(request.getNickname())).willReturn(true);

        // when, then
        Assertions.assertThatThrownBy(() -> authService.signUp(request)).isInstanceOf(DuplicateInstanceException.class);
        verify(saveUserPort, times(0)).saveUser(any());
    }
}