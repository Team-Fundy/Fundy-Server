package com.fundy.redis.refresh.repository;

import com.fundy.redis.refresh.model.RefreshInfoModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshInfoRepository extends CrudRepository<RefreshInfoModel, String> {
    RefreshInfoModel save(RefreshInfoModel refreshInfoModel);
    Optional<RefreshInfoModel> findById(String id);
    Optional<RefreshInfoModel> findByRefreshToken(String refreshToken);
}