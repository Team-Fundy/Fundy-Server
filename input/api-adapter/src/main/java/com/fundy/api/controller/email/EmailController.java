package com.fundy.api.controller.email;

import com.fundy.api.common.response.GlobalExceptionResponse;
import com.fundy.api.common.response.GlobalResponse;
import com.fundy.api.controller.email.dto.req.VerifyEmailRequestBody;
import com.fundy.application.email.in.IsVerifyEmailUseCase;
import com.fundy.application.email.in.SendVerifyCodeUseCase;
import com.fundy.application.email.in.dto.req.IsVerifyEmailRequest;
import com.fundy.application.email.in.dto.res.IsVerifyEmailResponse;
import com.fundy.application.email.in.dto.res.SendVerifyCodeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Email", description = "이메일 전송 API")
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {
    private final SendVerifyCodeUseCase sendVerifyCodeUseCase;
    private final IsVerifyEmailUseCase isVerifyEmailUseCase;

    @Operation(summary = "인증 메일 발송", description = "인증을 위한 코드 메일 발송 및 토큰 취득")
    @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "400", description = "에러 발생",
        content = @Content(schema = @Schema(implementation = GlobalExceptionResponse.class)))
    @PostMapping("/auth-code")
    public final GlobalResponse<SendVerifyCodeResponse> sendVerifyCode(
        @Parameter(description = "인증받을 이메일", example = "dongwon0103@naver.com", required = true)
        @RequestParam(name = "email") String email) {
        return GlobalResponse.<SendVerifyCodeResponse>builder()
            .message("인증 메일 발송")
            .result(sendVerifyCodeUseCase.sendVerifyCode(email))
            .build();
    }

    @Operation(summary = "메일 인증", description = "토큰과 코드로 메일 인증z")
    @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "400", description = "에러 발생",
        content = @Content(schema = @Schema(implementation = GlobalExceptionResponse.class)))
    @PostMapping("/verify")
    public final GlobalResponse<IsVerifyEmailResponse> verifyEmail(@RequestBody @Valid final VerifyEmailRequestBody requestBody) {
        return GlobalResponse.<IsVerifyEmailResponse>builder()
            .message("이메일 검증")
            .result(isVerifyEmailUseCase.isVerifyEmail(IsVerifyEmailRequest.builder()
                    .code(requestBody.getCode())
                    .token(requestBody.getToken())
                    .email(requestBody.getEmail())
                .build()))
            .build();
    }
}