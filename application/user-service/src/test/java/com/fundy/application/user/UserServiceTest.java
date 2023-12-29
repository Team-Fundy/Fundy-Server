package com.fundy.application.user;

import com.fundy.application.user.in.dto.res.IsAvailableNicknameResponse;
import com.fundy.application.user.out.ValidUserPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserService 유닛 테스트")
class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private ValidUserPort validUserPort;

    @DisplayName("[성공] 닉네임 사용 가능 여부")
    @Test
    void isAvailableNickname() {
        // given
        String nickname = "펀디 유저";
        given(validUserPort.existsByNickname(nickname)).willReturn(false);

        // when
        IsAvailableNicknameResponse result = userService.isAvailableNickname(nickname);

        // then
        Assertions.assertThat(result.getTargetNickname()).isEqualTo(nickname);
        Assertions.assertThat(result.isAvailable()).isTrue();
        verify(validUserPort, times(1)).existsByNickname(any());

    }

    @DisplayName("[실패] 닉네임 사용 가능 여부: 닉네임 포맷")
    @Test
    void isAvailableNicknameFailCaseWithNicknameFormat() {
        // given
        String nickname = "하";

        // when
        IsAvailableNicknameResponse result = userService.isAvailableNickname(nickname);

        // then
        Assertions.assertThat(result.getTargetNickname()).isEqualTo(nickname);
        Assertions.assertThat(result.isAvailable()).isFalse();
        verify(validUserPort, times(0)).existsByNickname(any());
    }

    @DisplayName("[실패] 닉네임 사용 가능 여부: 닉네임 중복")
    @Test
    void isAvailableNicknameFailCaseWithDuplicate() {
        // given
        String nickname = "펀디유저";
        given(validUserPort.existsByNickname(nickname)).willReturn(true);

        // when
        IsAvailableNicknameResponse result = userService.isAvailableNickname(nickname);

        // then
        Assertions.assertThat(result.getTargetNickname()).isEqualTo(nickname);
        Assertions.assertThat(result.isAvailable()).isFalse();
        verify(validUserPort, times(1)).existsByNickname(any());
    }
}