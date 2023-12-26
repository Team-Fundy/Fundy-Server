package com.fundy.jpa.user.adapter;

import com.fundy.application.user.out.LoadUserPort;
import com.fundy.application.user.out.SaveUserPort;
import com.fundy.application.user.out.ValidUserPort;
import com.fundy.application.user.out.command.SaveUserCommand;
import com.fundy.domain.user.User;
import com.fundy.jpa.user.mapper.UserMapper;
import com.fundy.jpa.user.model.UserModel;
import com.fundy.jpa.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserPersistenceAdapter implements SaveUserPort, LoadUserPort, ValidUserPort {
    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByNickname(String nickname) {
        return Optional.empty();
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User saveUser(SaveUserCommand command) {
        return mapper.entityToDomain(userRepository.save(UserModel.builder()
            .id(command.getId())
            .email(command.getEmail())
            .nickname(command.getNickname())
            .password(command.getPassword())
            .authorities(command.getAuthorities())
            .build()));
    }
}