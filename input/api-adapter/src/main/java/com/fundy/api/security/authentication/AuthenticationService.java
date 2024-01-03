package com.fundy.api.security.authentication;

import org.springframework.security.core.Authentication;

public interface AuthenticationService {
    Authentication getAuthentication(String email, String password);
}
