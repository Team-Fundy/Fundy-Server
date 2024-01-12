package com.fundy.redis.bantoken.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@RedisHash(value = "baned-token", timeToLive = (30 * 24 * 60 * 60))
public class BanedTokenModel {
    @Id
    private String id; // access Token
    @Indexed
    private String refreshToken;

    public static BanedTokenModel of(String accessToken, String refreshToken) {
        return new BanedTokenModel(accessToken, refreshToken);
    }
}
