package com.fundy.application.email;

import com.fundy.application.email.in.IsVerifyEmailUseCase;
import com.fundy.application.email.in.SendVerifyCodeUseCase;
import com.fundy.application.email.in.dto.req.IsVerifyEmailRequest;
import com.fundy.application.email.in.dto.res.IsVerifyEmailResponse;
import com.fundy.application.email.in.dto.res.SendVerifyCodeResponse;
import com.fundy.application.email.out.SendVerifyCodeEmailPort;
import com.fundy.application.exception.custom.ValidationException;
import com.fundy.application.user.in.IsAvailableEmailUseCase;
import com.fundy.domain.email.EmailTokenProvider;
import com.fundy.domain.email.dto.req.VerifyTokenRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailAuthService implements SendVerifyCodeUseCase, IsVerifyEmailUseCase {
    private final EmailTokenProvider emailTokenProvider = EmailTokenProvider.INSTANCE;
    private final SendVerifyCodeEmailPort sendVerifyCodeEmailPort;
    private final IsAvailableEmailUseCase isAvailableEmailUseCase;

    @Override
    public SendVerifyCodeResponse sendVerifyCode(final String email) {
        if(!isAvailableEmailUseCase.isAvailableEmail(email).isAvailable())
            throw new ValidationException("이메일을 사용할 수 없음");

        String code = RandomStringUtils.randomAlphanumeric(8);
        String token = emailTokenProvider.generateToken(email, code);

        sendVerifyCodeEmailPort.sendVerifyCode(email, code);

        return SendVerifyCodeResponse.of(email, token);
    }

    @Override
    public IsVerifyEmailResponse isVerifyEmail(final IsVerifyEmailRequest request) {
        if(!isAvailableEmailUseCase.isAvailableEmail(request.getEmail()).isAvailable())
            throw new ValidationException("이메일을 사용할 수 없음");

        return IsVerifyEmailResponse.of(request.getEmail(), emailTokenProvider.isVerifyToken(VerifyTokenRequest.builder()
                .token(request.getToken())
                .email(request.getEmail())
                .code(request.getCode())
            .build()));
    }
}
