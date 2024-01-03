package com.fundy.redis.refresh.adapter;

import com.fundy.application.user.out.SaveRefreshInfoPort;
import com.fundy.application.user.out.command.SaveRefreshInfoCommand;
import com.fundy.redis.refresh.model.RefreshInfoModel;
import com.fundy.redis.refresh.repository.RefreshInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshInfoPersistenceAdapter implements SaveRefreshInfoPort {
    private final RefreshInfoRepository refreshInfoRepository;

    @Override
    public void save(SaveRefreshInfoCommand command) {
        refreshInfoRepository.save(RefreshInfoModel.builder()
                .id(command.getEmail())
                .refreshToken(command.getRefreshToken())
                .authorities(command.getAuthorities())
            .build());
    }
}
