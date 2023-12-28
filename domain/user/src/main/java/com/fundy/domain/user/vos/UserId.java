package com.fundy.domain.user.vos;

import java.util.UUID;

public class UserId {
    private UUID id;

    private UserId() {
        this.id = UUID.randomUUID();
    }

    private UserId(UUID uuid) {
        this.id = uuid;
    }

    public static UserId newInstance() {
        return new UserId();
    }

    public static UserId of(UUID id) {
        if(id == null)
            throw new IllegalArgumentException("id가 존재하지 않습니다");
        return new UserId(id);
    }

    public UUID toUUID() {
        return id;
    }

    @Override
    public String toString() {
        return id.toString();
    }
}