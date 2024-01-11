package com.fundy.api.security.filter;

import com.fundy.api.security.authentication.AuthenticationHandler;
import com.fundy.application.user.in.IsVerifyAccessTokenUseCase;
import com.fundy.application.user.in.ResolveTokenUseCase;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final AuthenticationHandler authenticationHandler;
    private final IsVerifyAccessTokenUseCase isVerifyAccessTokenUseCase;
    private final ResolveTokenUseCase resolveTokenUseCase;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = resolveTokenUseCase.resolveToken(request.getHeader("Authorization"));

        if (!isReissueUrl(request) && isVerifyAccessTokenUseCase.isVerifyAccessToken(token))
            authenticationHandler.setAuthentication(token);
        filterChain.doFilter(request, response);
    }

    private boolean isReissueUrl(HttpServletRequest request) {
        return request.getRequestURI().equals("/auth/reissue");
    }
}