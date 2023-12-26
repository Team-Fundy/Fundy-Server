package com.fundy.api.controller.auth;

import com.fundy.api.common.response.GlobalExceptionResponse;
import com.fundy.api.common.response.GlobalResponse;
import com.fundy.api.controller.auth.req.SignUpRequestBody;
import com.fundy.application.user.in.SignUpUseCase;
import com.fundy.application.user.in.dto.req.SignUpRequest;
import com.fundy.application.user.in.dto.res.SignUpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Auth", description = "유저 회원가입 / 인증 / 정보 조회")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final SignUpUseCase signUpUseCase;

    @Operation(summary = "이메일 회원가입", description = "유저가 이메일로 회원가입")
    @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "400", description = "에러 발생",
        content = @Content(schema = @Schema(implementation = GlobalExceptionResponse.class)))
    @PostMapping("/sign-up")
    public final GlobalResponse<SignUpResponse> signUp(@RequestBody @Valid final SignUpRequestBody requestBody) {
        return GlobalResponse.<SignUpResponse>builder()
            .message("유저 생성 완료")
            .result(signUpUseCase.signUp(SignUpRequest.builder()
                    .email(requestBody.getEmail())
                    .nickname(requestBody.getNickname())
                    .password(requestBody.getPassword())
                .build()))
            .build();
    }
}