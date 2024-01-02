package com.fundy.api.controller.email.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "유저 이메일 인증 Request")
public class VerifyEmailRequestBody {
    @Email(message = "올바른 이메일 형식이 아닙니다")
    @NotNull(message = "이메일은 필수 입니다")
    @Schema(description = "이메일", example = "dongwon0103@naver.com")
    String email;

    @NotNull(message = "토큰은 필수 입니다")
    @Schema(description = "토큰", example = "akdfjklafjdklafjdkl")
    String token;

    @NotNull(message = "코드는 필수 입니다")
    @Schema(description = "코드", example = "AbjkFI01Q")
    String code;
}