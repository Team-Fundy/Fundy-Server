package com.fundy.api.security.authentication;

import org.springframework.security.core.Authentication;

public interface AuthenticationHandler {
    Authentication getAuthentication(String email, String password);
    void setAuthentication(String accessToken);
}
