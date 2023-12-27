package com.fundy.application.user;

import com.fundy.application.user.in.IsAvailableNicknameUseCase;
import com.fundy.application.user.in.dto.res.IsAvailableNicknameResponse;
import com.fundy.application.user.out.ValidUserPort;
import com.fundy.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class UserService implements IsAvailableNicknameUseCase {
    private final ValidUserPort validUserPort;

    @Override
    public IsAvailableNicknameResponse isAvailableNickname(String nickname) {
        boolean isAvailableNickname = User.validateNickname(nickname) && !validUserPort.existsByNickname(nickname);

        return IsAvailableNicknameResponse.of(nickname, isAvailableNickname);
    }
}
