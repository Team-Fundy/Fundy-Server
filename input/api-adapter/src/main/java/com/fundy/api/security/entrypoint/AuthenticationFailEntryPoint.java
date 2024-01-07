package com.fundy.api.security.entrypoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fundy.api.common.response.GlobalExceptionResponse;
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
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("authentication handler", authException);
        switch (request.getRequestURI().substring(request.getContextPath().length())) {
            case "/auth/sign-in":
                setNormallyResponse(HttpStatus.UNAUTHORIZED, response, "아이디 혹은 비밀 번호가 맞지 않습니다");
        }
    }

    private void setNormallyResponse(HttpStatus status, HttpServletResponse response, String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json;charset=UTF-8"); // MediaType.APPLICATION_JSON => 인코딩 문제 존재
        response.getWriter().write(mapper.writeValueAsString(GlobalExceptionResponse.builder()
            .message(message).build()));
    }
}
