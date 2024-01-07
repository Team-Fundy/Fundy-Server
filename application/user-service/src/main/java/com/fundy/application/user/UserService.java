package com.fundy.application.user;

import com.fundy.application.exception.custom.NoInstanceException;
import com.fundy.application.user.in.FindUserInfoByEmailUseCase;
import com.fundy.application.user.in.dto.res.UserInfoResponse;
import com.fundy.application.user.out.LoadUserPort;
import com.fundy.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserService implements FindUserInfoByEmailUseCase {
    private final LoadUserPort loadUserPort;

    @Override
    public UserInfoResponse findByEmail(String email) {
        User user = loadUserPort.findByEmail(email).orElseThrow(
            () -> new NoInstanceException("유저가 존재하지 않음"));

        return UserInfoResponse.builder()
            .email(user.getEmailAddress())
            .profile(user.getProfileUrl())
            .nickname(user.getNickname())
            .build();
    }
}
