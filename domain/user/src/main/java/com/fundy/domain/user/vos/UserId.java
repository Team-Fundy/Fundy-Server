package com.fundy.domain.user.vos;

import java.util.UUID;

public class UserId {
    private UUID id;

    private UserId() {
        this.id = UUID.randomUUID();
    }

    public static UserId newInstance() {
        return new UserId();
    }

    public UUID toUUID() {
        return id;
    }

    @Override
    public String toString() {
        return id.toString();
    }
}