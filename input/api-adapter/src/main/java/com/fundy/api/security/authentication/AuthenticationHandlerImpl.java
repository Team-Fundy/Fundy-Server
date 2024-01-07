package com.fundy.api.security.authentication;

import com.fundy.application.user.in.GetTokenizationUserInfoUseCase;
import com.fundy.application.user.in.dto.res.TokenizationUserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class AuthenticationHandlerImpl implements AuthenticationHandler {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final GetTokenizationUserInfoUseCase getTokenizationUserInfoUseCase;

    @Override
    public Authentication getAuthentication(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            email,password);

        return authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    }

    @Override
    public void setAuthentication(String accessToken) {
        TokenizationUserInfoResponse response = getTokenizationUserInfoUseCase.getTokenizationUserInfoByAccessToken(accessToken);

        Collection<? extends GrantedAuthority> authorities = response.getAuthorities().stream().map(SimpleGrantedAuthority::new).toList();
        UserDetails userDetails = new User(
            response.getEmail(), "", authorities);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
