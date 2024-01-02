package com.fundy.application.user;

import com.fundy.application.user.in.IsAvailableEmailUseCase;
import com.fundy.application.user.in.IsAvailableNicknameUseCase;
import com.fundy.application.user.in.dto.res.IsAvailableEmailResponse;
import com.fundy.application.user.in.dto.res.IsAvailableNicknameResponse;
import com.fundy.application.user.out.ValidUserPort;
import com.fundy.domain.user.User;
import com.fundy.domain.user.vos.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class ValidUserService implements IsAvailableNicknameUseCase, IsAvailableEmailUseCase {
    private final ValidUserPort validUserPort;

    @Override
    public IsAvailableNicknameResponse isAvailableNickname(String nickname) {
        return IsAvailableNicknameResponse.of(nickname,
            User.validateNickname(nickname) && !validUserPort.existsByNickname(nickname));
    }

    @Override
    public IsAvailableEmailResponse isAvailableEmail(String email) {
        return IsAvailableEmailResponse.of(email,
            Email.isEmailFormat(email) && !validUserPort.existsByEmail(email));
    }
}
