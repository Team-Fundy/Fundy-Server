package com.fundy.redis.bantoken.adapter;

import com.fundy.application.user.out.LoadBanedTokenPort;
import com.fundy.application.user.out.SaveBanedTokenPort;
import com.fundy.application.user.out.dto.req.SaveBanedTokenCommand;
import com.fundy.redis.bantoken.model.BanedTokenModel;
import com.fundy.redis.bantoken.repository.BanedTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BanedTokenPersistenceAdapter implements LoadBanedTokenPort, SaveBanedTokenPort {
    private final BanedTokenRepository banedTokenRepository;

    @Override
    public boolean existsByAccessToken(String accessToken) {
        return banedTokenRepository.existsById(accessToken);
    }

    @Override
    public boolean existsByRefreshToken(String refreshToken) {
        return banedTokenRepository.existsByRefreshToken(refreshToken);
    }

    @Override
    public void save(SaveBanedTokenCommand command) {
        banedTokenRepository.save(BanedTokenModel.of(command.getAccessToken(), command.getRefreshToken()));
    }
}
