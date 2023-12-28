package com.fundy.jpa.user.adapter;

import com.fundy.application.user.out.LoadUserPort;
import com.fundy.application.user.out.SaveUserPort;
import com.fundy.application.user.out.ValidUserPort;
import com.fundy.application.user.out.command.SaveUserCommand;
import com.fundy.domain.user.User;
import com.fundy.jpa.user.mapper.UserMapper;
import com.fundy.jpa.user.model.UserModel;
import com.fundy.jpa.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserPersistenceAdapter implements SaveUserPort, LoadUserPort, ValidUserPort {
    private final UserRepository userRepository;
    private final UserMapper mapper;
    @PersistenceContext
    private final EntityManager entityManager;

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
    public UUID saveUser(SaveUserCommand command) {
        return userRepository.save(UserModel.builder()
            .email(command.getEmail())
            .nickname(command.getNickname())
            .password(command.getPassword())
            .authorities(command.getAuthorities())
            .build()).getId();
    }
}