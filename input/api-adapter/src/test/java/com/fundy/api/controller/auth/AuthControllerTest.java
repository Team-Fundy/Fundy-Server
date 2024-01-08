package com.fundy.api.controller.auth;

import com.fundy.api.BaseIntegrationTest;
import com.fundy.api.controller.auth.req.SignUpRequestBody;
import com.fundy.application.user.out.SaveUserPort;
import com.fundy.application.user.out.command.SaveUserCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Auth 통합 테스트")
class AuthControllerTest extends BaseIntegrationTest {
    @Autowired
    private SaveUserPort saveUserPort;

    @DisplayName("[성공] 회원 가입")
    @Test
    void signUp() throws Exception {
        // given
        SignUpRequestBody requestBody = SignUpRequestBody.builder()
            .email("dong0103@naver.com")
            .nickname("fundy-user")
            .password("#Fundy22310dd")
            .build();

        // when
        ResultActions resultActions = mvc.perform(post("/auth/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(requestBody))
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print());

        // then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.result.email").value(requestBody.getEmail()));
        resultActions.andExpect(jsonPath("$.result.nickname").value(requestBody.getNickname()));
    }

    @DisplayName("[실패] 회원 가입: 이메일 포맷")
    @Test
    void signUpFailCaseWithEmailFormat() throws Exception {
        // given
        SignUpRequestBody requestBody = SignUpRequestBody.builder()
            .email("dong0naver.com")
            .nickname("fundy-user")
            .password("#Fundy22310dd")
            .build();

        // when
        ResultActions resultActions = mvc.perform(post("/auth/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(requestBody))
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print());

        // then
        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.message").isArray());
    }

    @DisplayName("[실패] 회원 가입: 중복 닉네임")
    @Test
    void signUpFailCaseWithDuplicateNickname() throws Exception {
        // given
        SignUpRequestBody requestBody = SignUpRequestBody.builder()
            .email("dong0@naver.com")
            .nickname("fundy-user")
            .password("#Fundy22310dd")
            .build();

        saveUserPort.saveUser(SaveUserCommand.builder()
                .email("email@naver.com")
                .nickname(requestBody.getNickname())
                .password("password")
            .build());

        // when
        ResultActions resultActions = mvc.perform(post("/auth/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(requestBody))
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print());

        // then
        resultActions.andExpect(status().isConflict());
    }
}