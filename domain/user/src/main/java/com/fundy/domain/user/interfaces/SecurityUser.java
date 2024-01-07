package com.fundy.domain.user.interfaces;

public interface SecurityUser extends AuthUser {

    String getEncodedPassword();
}
