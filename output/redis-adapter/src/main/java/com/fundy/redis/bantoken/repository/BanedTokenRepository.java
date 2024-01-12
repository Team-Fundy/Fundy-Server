package com.fundy.redis.bantoken.repository;

import com.fundy.redis.bantoken.model.BanedTokenModel;
import org.springframework.data.repository.CrudRepository;

public interface BanedTokenRepository extends CrudRepository<BanedTokenModel, String> {
    boolean existsById(String id);
    boolean existsByRefreshToken(String refreshToken);
}
