package com.fundy.redis.refresh.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
@RedisHash(value = "refresh", timeToLive = (30 * 24 * 60 * 60))
public class RefreshInfoModel {
    @Id
    private String id; // email -> id로 설정해야 에러 안남
    private List<String> authorities;
    private String nickname;
    private String profile;
    @Indexed
    private String refreshToken;

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}