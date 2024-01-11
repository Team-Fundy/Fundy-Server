package com.fundy.api.security.entrypoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fundy.api.common.response.GlobalCanRefreshResponse;
import com.fundy.api.common.response.GlobalExceptionResponse;
import com.fundy.application.user.in.CanTokenRefreshUseCase;
import com.fundy.application.user.in.ResolveTokenUseCase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthenticationFailEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper mapper = new ObjectMapper();
    private final ResolveTokenUseCase resolveTokenUseCase;
    private final CanTokenRefreshUseCase canTokenRefreshUseCase;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("authentication fail entry point", authException);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8"); // MediaType.APPLICATION_JSON => 인코딩 문제 존재

        switch (request.getRequestURI().substring(request.getContextPath().length())) {
            case "/auth/sign-in":
                response.getWriter().write(mapper.writeValueAsString(GlobalExceptionResponse.builder()
                    .message("아이디 혹은 비밀 번호가 올바르지 않습니다").build()));
                break;
            default:
                handleTokenIssue(response, resolveTokenUseCase.resolveToken(request.getHeader("Authorization")));
                break;
        }
    }

    private void handleTokenIssue(HttpServletResponse response, String accessToken) throws IOException {
        if (accessToken == null) {
            setNormallyResponse(response,"올바르지 않은 토큰");
            return;
        }

        response.getWriter().write(mapper.writeValueAsString(GlobalCanRefreshResponse.builder()
            .message("토큰 만료")
            .canRefresh(canTokenRefreshUseCase.canRefresh(accessToken))
            .build()));
    }

    private void setNormallyResponse(HttpServletResponse response, String message) throws IOException {
        response.getWriter().write(mapper.writeValueAsString(GlobalExceptionResponse.builder()
            .message(message).build()));
    }
}
