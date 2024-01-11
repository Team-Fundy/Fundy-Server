package com.fundy.application.user.out;

import com.fundy.domain.user.interfaces.TokenizationUser;

import java.util.Optional;

public interface LoadRefreshInfoPort {
     Optional<TokenizationUser> findByRefreshToken(String refreshToken);
}
