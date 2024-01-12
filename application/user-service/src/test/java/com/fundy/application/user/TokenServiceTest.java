package com.fundy.application.user;

import com.fundy.application.exception.custom.NoInstanceException;
import com.fundy.application.exception.custom.UnAuthorizedException;
import com.fundy.application.user.in.dto.res.TokenInfoResponse;
import com.fundy.application.user.in.dto.res.TokenizationUserInfoResponse;
import com.fundy.application.user.out.LoadBanedTokenPort;
import com.fundy.application.user.out.LoadRefreshInfoPort;
import com.fundy.application.user.out.LoadUserPort;
import com.fundy.application.user.out.SaveRefreshInfoPort;
import com.fundy.application.user.out.UpdateRefreshInfoPort;
import com.fundy.domain.user.TokenInfo;
import com.fundy.domain.user.User;
import com.fundy.domain.user.UserTokenProvider;
import com.fundy.domain.user.enums.Authority;
import com.fundy.domain.user.interfaces.TokenizationUser;
import com.fundy.domain.user.vos.Email;
import com.fundy.domain.user.vos.Image;
import com.fundy.domain.user.vos.Nickname;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("토큰 서비스 유닛 테스트")
class TokenServiceTest {
    @InjectMocks
    private TokenService tokenService;
    @Mock
    private LoadUserPort loadUserPort;
    @Mock
    private SaveRefreshInfoPort saveRefreshInfoPort;
    @Mock
    private LoadRefreshInfoPort loadRefreshInfoPort;
    @Mock
    private LoadBanedTokenPort loadBanedTokenPort;

    @Mock
    private UpdateRefreshInfoPort updateRefreshInfoPort;

    @DisplayName("[성공] 토큰 생성")
    @Test
    void generateToken() {
        // given
        String email = "don01@naver.com";
        given(loadUserPort.findByEmail(email)).willReturn(Optional.of(User.builder()
                .email(Email.of(email))
                .nickname(Nickname.of("nickname"))
                .profile(Image.of("http://www.naver.com"))
                .authorities(Collections.singletonList(Authority.NORMAL))
            .build()));

        // when
        TokenInfoResponse result = tokenService.generateToken(email);

        // then
        Assertions.assertThat(result.getGrantType()).isNotEmpty();
        Assertions.assertThat(result.getAccessToken()).isNotEmpty();
        Assertions.assertThat(result.getRefreshToken()).isNotEmpty();
        verify(loadUserPort, times(1)).findByEmail(any());
        verify(saveRefreshInfoPort, times(1)).save(any());
    }

    @DisplayName("[실패] 토큰 생성: 유저 존재하지 않음")
    @Test
    void isVerifyAccessToken() {
        // given
        String email = "don01@naver.com";
        given(loadUserPort.findByEmail(email)).willReturn(Optional.empty());

        // when, Then
        Assertions.assertThatThrownBy(() -> tokenService.generateToken(email)).isInstanceOf(NoInstanceException.class);
        verify(loadUserPort, times(1)).findByEmail(any());
        verify(saveRefreshInfoPort, times(0)).save(any());
    }

    @DisplayName("[성공] 토큰으로 유저 조회")
    @Test
    void getTokenizationUserInfoByAccessToken() {
        // given
        TokenizationUser user = User.builder()
            .email(Email.of("dt10@naver.com"))
            .authorities(Collections.singletonList(Authority.NORMAL))
            .profile(Image.of("http://www.naver.com"))
            .nickname(Nickname.of("nickn"))
            .build();

        TokenInfo tokenInfo = UserTokenProvider.INSTANCE.generateToken(user);

        // when
        TokenizationUserInfoResponse result = tokenService.getTokenizationUserInfoByAccessToken(tokenInfo.getAccessToken());

        // then
        Assertions.assertThat(result.getEmail()).isEqualTo(user.getEmailAddress());
        Assertions.assertThat(result.getNickname()).isEqualTo(user.getNickname());
        Assertions.assertThat(result.getAuthorities()).isEqualTo(user.getAuthorities());
        Assertions.assertThat(result.getProfile()).isEqualTo(user.getProfileUrl());
    }

    @DisplayName("[실패] 토큰으로 유저 조회: 올바르지 않은 토큰")
    @Test
    void getTokenizationUserInfoByAccessTokenFail() {
        // given
        String invalidToken = "invalid_Token";

        // when, then
        Assertions.assertThatThrownBy(
            () -> tokenService.getTokenizationUserInfoByAccessToken(invalidToken)).isInstanceOf(UnAuthorizedException.class);
    }

    @DisplayName("[성공] 토큰 추출")
    @Test
    void resolveToken() {
        // given
        TokenizationUser user = User.builder()
            .email(Email.of("dt10@naver.com"))
            .authorities(Collections.singletonList(Authority.NORMAL))
            .profile(Image.of("http://www.naver.com"))
            .nickname(Nickname.of("nickn"))
            .build();

        TokenInfo tokenInfo = UserTokenProvider.INSTANCE.generateToken(user);

        // when
        String resolvedToken = tokenService.resolveToken(String.format("%s %s", tokenInfo.getGrantType(), tokenInfo.getAccessToken()));

        // then
        Assertions.assertThat(resolvedToken).isEqualTo(tokenInfo.getAccessToken());
    }

    @DisplayName("[실패] 토큰 추출: Grant Type")
    @Test
    void resolveTokenFailWithInvalidGrantType() {
        // given
        TokenizationUser user = User.builder()
            .email(Email.of("dt10@naver.com"))
            .authorities(Collections.singletonList(Authority.NORMAL))
            .profile(Image.of("http://www.naver.com"))
            .nickname(Nickname.of("nickn"))
            .build();

        String invalidGrantType = "Bearel";
        TokenInfo tokenInfo = UserTokenProvider.INSTANCE.generateToken(user);

        // when
        String resolvedToken = tokenService.resolveToken(String.format("%s %s", invalidGrantType, tokenInfo.getAccessToken()));

        // then
        Assertions.assertThat(resolvedToken).isNotEqualTo(tokenInfo.getAccessToken());
    }

    @DisplayName("[실패] 토큰 추출: Grant Type을 빼먹음")
    @Test
    void resolveTokenFailWithNotGrantType() {
        // given
        TokenizationUser user = User.builder()
            .email(Email.of("dt10@naver.com"))
            .authorities(Collections.singletonList(Authority.NORMAL))
            .profile(Image.of("http://www.naver.com"))
            .nickname(Nickname.of("nickn"))
            .build();

        TokenInfo tokenInfo = UserTokenProvider.INSTANCE.generateToken(user);

        // when
        String resolvedToken = tokenService.resolveToken(tokenInfo.getAccessToken());

        // then
        Assertions.assertThat(resolvedToken).isNull();
    }

    @DisplayName("[성공] 토큰 리프레쉬 가능 여부")
    @Test
    void canRefresh() {
        // given
        TokenInfo tokenInfo = UserTokenProvider.INSTANCE.generateToken(User.builder()
                .email(Email.of("do1@naver.com"))
                .nickname(Nickname.of("nicknam"))
                .profile(Image.of("http://www.naver.com"))
                .authorities(Collections.singletonList(Authority.NORMAL))
            .build());

        given(loadBanedTokenPort.existsByAccessToken(tokenInfo.getAccessToken())).willReturn(false);

        // when
        boolean result = tokenService.canRefresh(tokenInfo.getAccessToken());

        // then
        Assertions.assertThat(result).isTrue();
    }

    @DisplayName("[실패] 토큰 리프레쉬 가능 여부: 밴 당한 토큰")
    @Test
    void canRefreshFailWithBaned() {
        // given
        TokenInfo tokenInfo = UserTokenProvider.INSTANCE.generateToken(User.builder()
            .email(Email.of("do1@naver.com"))
            .nickname(Nickname.of("nicknam"))
            .profile(Image.of("http://www.naver.com"))
            .authorities(Collections.singletonList(Authority.NORMAL))
            .build());

        given(loadBanedTokenPort.existsByAccessToken(tokenInfo.getAccessToken())).willReturn(true);

        // when
        boolean result = tokenService.canRefresh(tokenInfo.getAccessToken());

        // then
        Assertions.assertThat(result).isFalse();
    }

    @DisplayName("[실패] 토큰 리프레쉬 가능 여부: 옳지 못한 토큰")
    @Test
    void canRefreshFailWithInvalidToken() {
        // given
        String invalidToken = "invalid_token";

        // when
        boolean result = tokenService.canRefresh(invalidToken);

        // then
        Assertions.assertThat(result).isFalse();
    }

    @DisplayName("[성공] 토큰 재발급")
    @Test
    void reissue() {
        // given
        TokenizationUser user = User.builder()
            .email(Email.of("do1@naver.com"))
            .nickname(Nickname.of("nicknam"))
            .profile(Image.of("http://www.naver.com"))
            .authorities(Collections.singletonList(Authority.NORMAL))
            .build();

        TokenInfo tokenInfo = UserTokenProvider.INSTANCE.generateToken(user);
        given(loadRefreshInfoPort.findByRefreshToken(tokenInfo.getRefreshToken())).willReturn(Optional.of(user));

        // when
        TokenInfoResponse response = tokenService.reissue(tokenInfo.getRefreshToken());

        // then
        Assertions.assertThat(response.getGrantType()).isEqualTo(UserTokenProvider.INSTANCE.getGrantType());
        Assertions.assertThat(response.getAccessToken()).isNotEmpty();
        Assertions.assertThat(response.getRefreshToken()).isNotEmpty();
        verify(loadRefreshInfoPort, times(1)).findByRefreshToken(any());
    }

    @DisplayName("[실패] 토큰 재발급: 밴당한 토큰")
    @Test
    void reissueFailWithBanned() {
        // given
        TokenizationUser user = User.builder()
            .email(Email.of("do1@naver.com"))
            .nickname(Nickname.of("nicknam"))
            .profile(Image.of("http://www.naver.com"))
            .authorities(Collections.singletonList(Authority.NORMAL))
            .build();

        TokenInfo tokenInfo = UserTokenProvider.INSTANCE.generateToken(user);

        given(loadBanedTokenPort.existsByRefreshToken(tokenInfo.getRefreshToken())).willReturn(true);

        // when, then
        Assertions.assertThatThrownBy(
            ()->tokenService.reissue(tokenInfo.getRefreshToken()))
            .isInstanceOf(UnAuthorizedException.class);
    }

    @DisplayName("[실패] 토큰 재발급: 잘못된 토큰")
    @Test
    void reissueFailWithInvalidToken() {
        // given
        String invalidToken = "invalidTOken";

        // when, then
        Assertions.assertThatThrownBy(()->tokenService.reissue(invalidToken)).isInstanceOf(UnAuthorizedException.class);
    }

    @DisplayName("[실패] 토큰 재발급: 저장된 리프레쉬 정보 없음")
    @Test
    void reissueFailWithEmptyRefreshInfo() {
        // given
        TokenInfo tokenInfo = UserTokenProvider.INSTANCE.generateToken(User.builder()
            .email(Email.of("do1@naver.com"))
            .nickname(Nickname.of("nicknam"))
            .profile(Image.of("http://www.naver.com"))
            .authorities(Collections.singletonList(Authority.NORMAL))
            .build());
        given(loadRefreshInfoPort.findByRefreshToken(tokenInfo.getRefreshToken())).willReturn(Optional.empty());

        // when, then
        Assertions.assertThatThrownBy(()->tokenService.reissue(tokenInfo.getRefreshToken())).isInstanceOf(UnAuthorizedException.class);
        verify(loadRefreshInfoPort, times(1)).findByRefreshToken(any());
    }
}