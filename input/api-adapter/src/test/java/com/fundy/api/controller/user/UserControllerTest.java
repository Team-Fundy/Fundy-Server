package com.fundy.api.controller.user;

import com.fundy.api.BaseIntegrationTest;
import com.fundy.application.user.out.SaveUserPort;
import com.fundy.application.user.out.dto.req.SaveUserCommand;
import com.fundy.domain.user.User;
import com.fundy.domain.user.UserTokenProvider;
import com.fundy.domain.user.TokenInfo;
import com.fundy.domain.user.enums.Authority;
import com.fundy.domain.user.vos.Email;
import com.fundy.domain.user.vos.Image;
import com.fundy.domain.user.vos.Nickname;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

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

    @DisplayName("[성공] 토큰으로 유저 정보 조회")
    @Test
    void getUserInfo() throws Exception {
        // given
        String emailAddress = "don0103@naver.com";
        String nickname = "nickname";
        String profile = "http://www.naver.com";
        List<Authority> authorities = Arrays.asList(Authority.NORMAL);
        TokenInfo tokenInfo = UserTokenProvider.INSTANCE.generateToken(User.builder()
                .email(Email.of(emailAddress))
                .profile(Image.of(profile))
                .nickname(Nickname.of(nickname))
                .authorities(authorities)
            .build());

        // when
        ResultActions resultActions = mvc.perform(get("/user/info")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", String.format("%s %s", tokenInfo.getGrantType(), tokenInfo.getAccessToken()))
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print());

        // then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.result.email").value(emailAddress));
        resultActions.andExpect(jsonPath("$.result.nickname").value(nickname));
        resultActions.andExpect(jsonPath("$.result.profile").value(profile));
        resultActions.andExpect(jsonPath("$.result.authorities.length()").value(authorities.size()));
    }

    @DisplayName("[실패] 토큰으로 유저 정보 조회: 잘못된 토큰")
    @Test
    void getUserInfoFailWithInvalidToken() throws Exception {
        // given
        String invalidToken = "invalidToken";

        // when
        ResultActions resultActions = mvc.perform(get("/user/info")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", String.format("%s %s", UserTokenProvider.INSTANCE.getGrantType(), invalidToken))
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print());

        // then
        resultActions.andExpect(status().isUnauthorized());
    }
}