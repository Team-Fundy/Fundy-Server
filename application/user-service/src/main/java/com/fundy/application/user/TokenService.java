package com.fundy.application.user;

import com.fundy.application.exception.custom.NoInstanceException;
import com.fundy.application.exception.custom.UnAuthorizedException;
import com.fundy.application.exception.custom.ValidationException;
import com.fundy.application.user.in.CanTokenRefreshUseCase;
import com.fundy.application.user.in.GenerateTokenUseCase;
import com.fundy.application.user.in.GetTokenizationUserInfoUseCase;
import com.fundy.application.user.in.IsVerifyAccessTokenUseCase;
import com.fundy.application.user.in.ReissueByRefreshTokenUseCase;
import com.fundy.application.user.in.ResolveTokenUseCase;
import com.fundy.application.user.in.dto.res.TokenInfoResponse;
import com.fundy.application.user.in.dto.res.TokenizationUserInfoResponse;
import com.fundy.application.user.out.LoadRefreshInfoPort;
import com.fundy.application.user.out.LoadUserPort;
import com.fundy.application.user.out.SaveRefreshInfoPort;
import com.fundy.application.user.out.dto.req.SaveRefreshInfoCommand;
import com.fundy.domain.user.TokenInfo;
import com.fundy.domain.user.UserTokenProvider;
import com.fundy.domain.user.interfaces.TokenizationUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TokenService implements GenerateTokenUseCase, IsVerifyAccessTokenUseCase,
    GetTokenizationUserInfoUseCase, ResolveTokenUseCase,
    CanTokenRefreshUseCase, ReissueByRefreshTokenUseCase {
    private final UserTokenProvider userTokenProvider = UserTokenProvider.INSTANCE;
    private final LoadUserPort loadUserPort;
    private final SaveRefreshInfoPort saveRefreshInfoPort;
    private final LoadRefreshInfoPort loadRefreshInfoPort;

    @Transactional
    @Override
    public TokenInfoResponse generateToken(String email) {
        try {
            return tryGenerateToken(email);
        } catch (IllegalArgumentException e) {
            log.error("에러 발생", e);
            throw new ValidationException("이메일 / 권한 양식이 맞지 않습니다");
        }
    }

    private TokenInfoResponse tryGenerateToken(String email) {
        TokenizationUser user = loadUserPort.findByEmail(email).orElseThrow(
            () -> new NoInstanceException("유저가 존재하지 않음"));

        TokenInfo tokenInfo = userTokenProvider.generateToken(user);

        saveRefreshInfoPort.save(SaveRefreshInfoCommand.builder()
                .profile(user.getProfileUrl())
                .refreshToken(tokenInfo.getRefreshToken())
                .nickname(user.getNickname())
                .authorities(user.getAuthorities())
                .email(user.getEmailAddress())
            .build());

        return TokenInfoResponse.builder()
            .grantType(tokenInfo.getGrantType())
            .accessToken(tokenInfo.getAccessToken())
            .refreshToken(tokenInfo.getRefreshToken())
            .build();
    }

    @Override
    public boolean isVerifyAccessToken(String accessToken) {
        if (accessToken == null)
            return false;
        return userTokenProvider.isVerifyAccessToken(accessToken);
    }

    @Override
    public TokenizationUserInfoResponse getTokenizationUserInfoByAccessToken(String accessToken) {
        TokenizationUser user = userTokenProvider.getTokenizationUserByAccessToken(accessToken).orElseThrow(
            () -> new UnAuthorizedException("올바르지 않은 토큰 정보"));

        return TokenizationUserInfoResponse.builder()
            .email(user.getEmailAddress())
            .nickname(user.getNickname())
            .authorities(user.getAuthorities())
            .profile(user.getProfileUrl())
            .build();
    }

    @Override
    public String resolveToken(String resolveTarget) {
        if (StringUtils.hasText(resolveTarget) && resolveTarget.startsWith(userTokenProvider.getGrantType()))
            return resolveTarget.substring(userTokenProvider.getGrantType().length()+1); // 띄어쓰기 때문에

        return null;
    }

    @Override
    public boolean canRefresh(String accessToken) {
        if (accessToken == null)
            return false;
        return userTokenProvider.canRefresh(accessToken);
    }

    @Override
    public TokenInfoResponse reissue(String refreshToken) {
        if (!userTokenProvider.isVerifyRefreshToken(refreshToken))
            throw new UnAuthorizedException("리프레쉬 토큰을 사용할 수 없음");

        TokenInfo tokenInfo = userTokenProvider.generateToken(loadRefreshInfoPort.findByRefreshToken(refreshToken).orElseThrow(
            () -> new UnAuthorizedException("리프레쉬 토큰 정보가 없습니다")));

        return TokenInfoResponse.builder()
            .grantType(tokenInfo.getGrantType())
            .accessToken(tokenInfo.getAccessToken())
            .refreshToken(tokenInfo.getRefreshToken())
            .build();
    }
}