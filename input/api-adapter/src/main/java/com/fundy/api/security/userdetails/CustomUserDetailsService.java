package com.fundy.api.security.userdetails;

import com.fundy.application.user.in.GetSecurityInfoUseCase;
import com.fundy.application.user.in.dto.res.SecurityInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final GetSecurityInfoUseCase getSecurityInfoUseCase;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SecurityInfoResponse response = getSecurityInfoUseCase.getSecurityInfoByEmail(username);
        return CustomUserDetails.builder()
            .email(response.getEmail())
            .authorities(response.getAuthorities())
            .password(response.getPassword())
            .build();
    }
}
