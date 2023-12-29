package com.fundy.api.controller.user;

import com.fundy.api.BaseIntegrationTest;
import com.fundy.application.user.out.SaveUserPort;
import com.fundy.application.user.out.command.SaveUserCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("유저 통합 테스트")
class UserControllerTest extends BaseIntegrationTest {
    @Autowired
    private SaveUserPort saveUserPort;

    @DisplayName("[성공] 닉네임 사용 가능 여부")
    @Test
    void isAvailableNickname() throws Exception {
        // given
        String targetNickname = "유저";

        // when
        ResultActions resultActions = mvc.perform(get("/user/check-nickname")
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("nickname", targetNickname)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print());

        // then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.result.targetNickname").value(targetNickname));
        resultActions.andExpect(jsonPath("$.result.available").value(true));
    }

    @DisplayName("[실패] 닉네임 사용 가능 여부: 중복")
    @Test
    void isAvailableNicknameFailCaseWithDuplicate() throws Exception {
        // given
        String targetNickname = "유저";
        saveUserPort.saveUser(SaveUserCommand.builder()
                .email("mock@mock.com")
                .password("password")
                .nickname(targetNickname)
            .build());

        // when
        ResultActions resultActions = mvc.perform(get("/user/check-nickname")
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("nickname", targetNickname)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print());

        // then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.result.targetNickname").value(targetNickname));
        resultActions.andExpect(jsonPath("$.result.available").value(false));
    }

    @DisplayName("[실패] 닉네임 사용 가능 여부: 포맷")
    @Test
    void isAvailableNicknameFailCaseWithFormat() throws Exception {
        // given
        String targetNickname = "유";

        // when
        ResultActions resultActions = mvc.perform(get("/user/check-nickname")
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("nickname", targetNickname)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print());

        // then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.result.targetNickname").value(targetNickname));
        resultActions.andExpect(jsonPath("$.result.available").value(false));
    }
}