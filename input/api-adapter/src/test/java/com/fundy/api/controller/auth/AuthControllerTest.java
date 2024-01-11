package com.fundy.api.controller.auth;

import com.fundy.api.BaseIntegrationTest;
import com.fundy.api.controller.auth.dto.req.SignInRequestBody;
import com.fundy.api.controller.auth.dto.req.SignUpRequestBody;
import com.fundy.application.user.out.SaveRefreshInfoPort;
import com.fundy.application.user.out.SaveUserPort;
import com.fundy.application.user.out.dto.req.SaveRefreshInfoCommand;
import com.fundy.application.user.out.dto.req.SaveUserCommand;
import com.fundy.domain.user.TokenInfo;
import com.fundy.domain.user.User;
import com.fundy.domain.user.UserTokenProvider;
import com.fundy.domain.user.enums.Authority;
import com.fundy.domain.user.interfaces.TokenizationUser;
import com.fundy.domain.user.vos.Email;
import com.fundy.domain.user.vos.Image;
import com.fundy.domain.user.vos.Nickname;
import com.fundy.domain.user.vos.Password;
import com.fundy.jpa.user.model.UserModel;
import com.fundy.jpa.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Auth 통합 테스트")
class AuthControllerTest extends BaseIntegrationTest {
    @Autowired
    private SaveUserPort saveUserPort;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SaveRefreshInfoPort saveRefreshInfoPort; // application output port 지향

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

    @DisplayName("[성공] 이메일 로그인")
    @Test
    void signIn() throws Exception {
        // given
        String email = "t10@naver.com";
        String password = "#Fundy01@ffdjkfj";

        userRepository.save(UserModel.builder()
            .email(email)
            .password(new BCryptPasswordEncoder().encode(password))
            .nickname("test01")
            .authorities(Collections.singletonList(Authority.NORMAL.name()))
            .profile("http://www.profile")
            .creatorProfile("http://www.profile")
            .creatorBackground("http://www.profile")
            .creatorDescription("description")
            .creatorName("creator")
            .build());

        SignInRequestBody requestBody = SignInRequestBody.builder()
            .email(email)
            .password(password)
            .build();

        // when
        ResultActions resultActions = mvc.perform(post("/auth/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(requestBody))
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print());

        // then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.result.accessToken").exists());
        resultActions.andExpect(jsonPath("$.result.refreshToken").exists());
    }

    @DisplayName("[실패] 이메일 로그인: 비밀번호가 올바르지 않음")
    @Test
    void signInFailCaseWithInvalidPassword() throws Exception {
        // given
        String email = "t10@naver.com";
        String password = "#Fundy01@ffdjkfj";

        saveUserPort.saveUser(SaveUserCommand.builder()
            .email(email)
            .password("invalid password")
            .nickname("test01")
            .authorities(Collections.singletonList(Authority.NORMAL.name()))
            .build());

        SignInRequestBody requestBody = SignInRequestBody.builder()
            .email(email)
            .password(password)
            .build();

        // when
        ResultActions resultActions = mvc.perform(post("/auth/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(requestBody))
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print());

        // then
        resultActions.andExpect(status().isUnauthorized());
    }

    @DisplayName("[성공] 토큰 재발급")
    @Test
    void reissue() throws Exception {
        // given
        TokenizationUser user = User.builder()
            .email(Email.of("fundy01@naver.com"))
            .nickname(Nickname.of("nick"))
            .profile(Image.of("http://www.naver.com"))
            .authorities(Collections.singletonList(Authority.NORMAL))
            .build();

        saveUserPort.saveUser(SaveUserCommand.builder()
            .email(user.getEmailAddress())
            .password(Password.createEncodedPassword("adkEla@lfjakdfn1").getEncodedPassword())
            .nickname(user.getNickname())
            .authorities(Collections.singletonList(Authority.NORMAL.name()))
            .build());

        TokenInfo tokenInfo = UserTokenProvider.INSTANCE.generateToken(user);

        saveRefreshInfoPort.save(SaveRefreshInfoCommand.builder()
                .authorities(user.getAuthorities())
                .email(user.getEmailAddress())
                .refreshToken(tokenInfo.getRefreshToken())
                .profile(user.getProfileUrl())
                .nickname(user.getNickname())
            .build());

        // when
        ResultActions resultActions = mvc.perform(get("/auth/reissue")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", String.format("%s %s", tokenInfo.getGrantType(), tokenInfo.getRefreshToken()))
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print());

        // then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.result.accessToken").isNotEmpty());
    }
}