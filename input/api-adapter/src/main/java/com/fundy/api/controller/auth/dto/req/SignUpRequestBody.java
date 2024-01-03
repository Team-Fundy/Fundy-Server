package com.fundy.api.controller.auth.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "유저 이메일 회원가입 Request")
public class SignUpRequestBody {
    @NotNull(message = "이메일은 필수 입니다")
    @Email(message = "올바른 이메일 형식이 아닙니다")
    @Schema(description = "유저 이메일", example = "dongwon0103@naver.com")
    private String email;

    // 대소문자, 숫자, 특수기호(@#$%^&+=!*)를 하나 이상 포함
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!*]).*$",message = "비밀번호 패턴이 옳지 않습니다")
    @NotNull(message = "비밀번호는 필수입니다")
    @Length(min = 10, max = 30, message = "비밀번호 길이는 10~30까지 입니다")
    @Schema(description = "유저 비밀번호", example = "@TestTestPassword123")
    private String password;

    @NotNull(message = "닉네임은 필수입니다")
    @Length(min = 2, max = 30, message = "닉네임 길이는 2~30 까지 입니다.")
    @Schema(description = "유저 닉네임", example = "펀디즈123")
    private String nickname;
}
