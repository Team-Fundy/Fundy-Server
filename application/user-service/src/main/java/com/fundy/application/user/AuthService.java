package com.fundy.application.user;

import com.fundy.application.exception.custom.DuplicateInstanceException;
import com.fundy.application.exception.custom.NoInstanceException;
import com.fundy.application.exception.custom.ValidationException;
import com.fundy.application.user.in.GetSecurityInfoUseCase;
import com.fundy.application.user.in.SignUpUseCase;
import com.fundy.application.user.in.dto.req.SignUpRequest;
import com.fundy.application.user.in.dto.res.SecurityInfoResponse;
import com.fundy.application.user.in.dto.res.SignUpResponse;
import com.fundy.application.user.out.LoadUserPort;
import com.fundy.application.user.out.SaveUserPort;
import com.fundy.application.user.out.ValidUserPort;
import com.fundy.application.user.out.command.SaveUserCommand;
import com.fundy.domain.user.User;
import com.fundy.domain.user.interfaces.SecurityUser;
import com.fundy.domain.user.vos.Email;
import com.fundy.domain.user.vos.Password;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AuthService implements SignUpUseCase, GetSecurityInfoUseCase {
    private final SaveUserPort saveUserPort;
    private final ValidUserPort validUserPort;
    private final LoadUserPort loadUserPort;

    @Transactional
    @Override
    public SignUpResponse signUp(final SignUpRequest signUpRequest) {
        try {
            return trySignUp(signUpRequest);
        } catch (IllegalArgumentException e) {
            log.error("에러 발생",e);
            throw new ValidationException("이메일 / 닉네임 / 비밀번호 양식이 맞지 않습니다");
        }
    }

    private SignUpResponse trySignUp(final SignUpRequest signUpRequest) {
        User user = User.emailSignUp(
            Email.of(signUpRequest.getEmail()),
            signUpRequest.getNickname(),
            Password.createEncodedPassword(signUpRequest.getPassword()));

        if (validUserPort.existsByEmail(signUpRequest.getEmail()) ||
            validUserPort.existsByNickname(signUpRequest.getNickname()))
            throw new DuplicateInstanceException("중복인 유저 존재");

        saveUserPort.saveUser(SaveUserCommand.builder()
            .email(user.getEmailAddress())
            .nickname(user.getNickname())
            .password(user.getEncodedPassword())
            .authorities(user.getAuthorities())
            .build());

        return SignUpResponse.builder()
            .email(signUpRequest.getEmail())
            .nickname(signUpRequest.getNickname())
            .build();
    }

    @Override
    public SecurityInfoResponse getSecurityInfoByEmail(String email) {
        SecurityUser user = loadUserPort.findByEmail(email).orElseThrow(
            () -> new NoInstanceException("유저가 존재하지 않음"));

        return SecurityInfoResponse.builder()
            .email(user.getEmailAddress())
            .password(user.getEncodedPassword())
            .authorities(user.getAuthorities())
            .build();
    }
}