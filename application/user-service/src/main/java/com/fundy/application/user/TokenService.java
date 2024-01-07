package com.fundy.application.user;

import com.fundy.application.exception.custom.NoInstanceException;
import com.fundy.application.exception.custom.UnAuthorizedException;
import com.fundy.application.exception.custom.ValidationException;
import com.fundy.application.user.in.GenerateTokenUseCase;
import com.fundy.application.user.in.GetTokenizationUserInfoUseCase;
import com.fundy.application.user.in.IsVerifyAccessTokenUseCase;
import com.fundy.application.user.in.ResolveTokenUseCase;
import com.fundy.application.user.in.dto.req.GenerateToeknRequest;
import com.fundy.application.user.in.dto.res.TokenInfoResponse;
import com.fundy.application.user.in.dto.res.TokenizationUserInfoResponse;
import com.fundy.application.user.out.LoadUserPort;
import com.fundy.application.user.out.SaveRefreshInfoPort;
import com.fundy.application.user.out.command.SaveRefreshInfoCommand;
import com.fundy.domain.user.UserTokenProvider;
import com.fundy.domain.user.dto.res.TokenInfo;
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
public class TokenService implements GenerateTokenUseCase, IsVerifyAccessTokenUseCase, GetTokenizationUserInfoUseCase, ResolveTokenUseCase {
    private final UserTokenProvider userTokenProvider = UserTokenProvider.INSTANCE;
    private final LoadUserPort loadUserPort;
    private final SaveRefreshInfoPort saveRefreshInfoPort;

    @Transactional
    @Override
    public TokenInfoResponse generateToken(GenerateToeknRequest request) {
        try {
            return tryGenerateToken(request);
        } catch (IllegalArgumentException e) {
            log.error("에러 발생", e);
            throw new ValidationException("이메일 / 권한 양식이 맞지 않습니다");
        }
    }

    private TokenInfoResponse tryGenerateToken(GenerateToeknRequest request) {
        TokenInfo tokenInfo = userTokenProvider.generateToken(loadUserPort.findByEmail(request.getEmail()).orElseThrow(
            () -> new NoInstanceException("유저가 존재하지 않음")));

        saveRefreshInfoPort.save(SaveRefreshInfoCommand.builder()
            .email(request.getEmail())
            .authorities(request.getAuthorities())
            .refreshToken(tokenInfo.getRefreshToken())
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
        return userTokenProvider.isVerifyAccessToken(accessToken).isVerify();
    }

    @Override
    public TokenizationUserInfoResponse getTokenizationUserInfoByAccessToken(String accessToken) {
        TokenizationUser user = userTokenProvider.getTokenizationUserInfo(accessToken).orElseThrow(
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
}
