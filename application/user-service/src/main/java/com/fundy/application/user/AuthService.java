package com.fundy.application.user;

import com.fundy.application.exception.CoreExceptionFactory;
import com.fundy.application.exception.CoreExceptionType;
import com.fundy.application.user.in.SignUpUseCase;
import com.fundy.application.user.in.dto.req.SignUpRequest;
import com.fundy.application.user.in.dto.res.SignUpResponse;
import com.fundy.application.user.out.SaveUserPort;
import com.fundy.application.user.out.ValidUserPort;
import com.fundy.application.user.out.command.SaveUserCommand;
import com.fundy.domain.user.User;
import com.fundy.domain.user.vos.Email;
import com.fundy.domain.user.vos.Password;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService implements SignUpUseCase {
    private final SaveUserPort saveUserPort;
    private final ValidUserPort validUserPort;

    @Transactional
    @Override
    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        if (validUserPort.existsByEmail(signUpRequest.getEmail()) ||
            validUserPort.existsByEmail(signUpRequest.getNickname()))
            throw CoreExceptionFactory.createWithMessage(CoreExceptionType.DUPLICATE, "중복인 유저 존재");

        User user = User.emailSignUp(
            Email.of(signUpRequest.getEmail()),
            signUpRequest.getNickname(),
            Password.createEncodedPassword(signUpRequest.getPassword()));

        saveUserPort.saveUser(SaveUserCommand.builder()
                .id(user.getId().toUUID())
                .email(user.getEmail().getAddress())
                .nickname(user.getNickname())
                .password(user.getPassword())
                .authorities(user.getAuthorities())
            .build());

        return SignUpResponse.builder()
            .email(signUpRequest.getEmail())
            .nickname(signUpRequest.getNickname())
            .build();
    }
}