package com.fundy.redis.refresh.adapter;

import com.fundy.application.user.out.LoadRefreshInfoPort;
import com.fundy.application.user.out.SaveRefreshInfoPort;
import com.fundy.application.user.out.UpdateRefreshInfoPort;
import com.fundy.application.user.out.dto.req.SaveRefreshInfoCommand;
import com.fundy.application.user.out.dto.req.UpdateRefreshTokenCommand;
import com.fundy.domain.user.interfaces.TokenizationUser;
import com.fundy.redis.refresh.mapper.RefreshMapper;
import com.fundy.redis.refresh.model.RefreshInfoModel;
import com.fundy.redis.refresh.repository.RefreshInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshInfoPersistenceAdapter implements SaveRefreshInfoPort, LoadRefreshInfoPort, UpdateRefreshInfoPort {
    private final RefreshInfoRepository refreshInfoRepository;
    private final RefreshMapper mapper;

    @Override
    public void save(SaveRefreshInfoCommand command) {
        refreshInfoRepository.save(RefreshInfoModel.builder()
                .id(command.getEmail())
                .refreshToken(command.getRefreshToken())
                .authorities(command.getAuthorities())
                .nickname(command.getNickname())
                .profile(command.getProfile())
            .build());
    }

    @Override
    public Optional<TokenizationUser> findByRefreshToken(String refreshToken) {
        return Optional.ofNullable(mapper.entityToDomain(
            refreshInfoRepository.findByRefreshToken(refreshToken).orElse(null)));
    }

    @Override
    public void updateRefreshToken(UpdateRefreshTokenCommand command) {
        RefreshInfoModel oldRefreshInfo = refreshInfoRepository.findByRefreshToken(command.getOldRefreshToken()).orElse(null);

        if (oldRefreshInfo == null)
            return;

        RefreshInfoModel newRefreshInfo = RefreshInfoModel.builder()
            .id(oldRefreshInfo.getId())
            .profile(oldRefreshInfo.getProfile())
            .nickname(oldRefreshInfo.getNickname())
            .authorities(oldRefreshInfo.getAuthorities())
            .refreshToken(command.getNewRefreshToken())
            .build();

        refreshInfoRepository.delete(oldRefreshInfo);
        refreshInfoRepository.save(newRefreshInfo);
    }
}
