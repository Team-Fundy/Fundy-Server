package com.fundy.api.controller.auth;

import com.fundy.api.common.response.GlobalExceptionResponse;
import com.fundy.api.common.response.GlobalResponse;
import com.fundy.api.controller.auth.dto.req.SignInRequestBody;
import com.fundy.api.controller.auth.dto.req.SignUpRequestBody;
import com.fundy.api.security.authentication.AuthenticationHandler;
import com.fundy.application.user.in.GenerateTokenUseCase;
import com.fundy.application.user.in.ReissueByRefreshTokenUseCase;
import com.fundy.application.user.in.ResolveTokenUseCase;
import com.fundy.application.user.in.SignUpUseCase;
import com.fundy.application.user.in.dto.req.SignUpRequest;
import com.fundy.application.user.in.dto.res.SignUpResponse;
import com.fundy.application.user.in.dto.res.TokenInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
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
    private final GenerateTokenUseCase generateTokenUseCase;
    private final AuthenticationHandler authenticationHandler;
    private final ReissueByRefreshTokenUseCase reissueByRefreshTokenUseCase;
    private final ResolveTokenUseCase resolveTokenUseCase;

    @Operation(summary = "이메일 회원가입", description = "유저가 이메일로 회원가입")
    @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "400", description = "에러 발생",
        content = @Content(schema = @Schema(implementation = GlobalExceptionResponse.class)))
    @PostMapping("/sign-up")
    public final GlobalResponse<SignUpResponse> signUp(@RequestBody @Valid final SignUpRequestBody requestBody) {
        return GlobalResponse.<SignUpResponse>builder()
            .message("유저 회원가입")
            .result(signUpUseCase.signUp(SignUpRequest.builder()
                    .email(requestBody.getEmail())
                    .nickname(requestBody.getNickname())
                    .password(requestBody.getPassword())
                .build()))
            .build();
    }

    @Operation(summary = "이메일 로그인", description = "유저가 이메일로 로그인")
    @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "400", description = "에러 발생",
        content = @Content(schema = @Schema(implementation = GlobalExceptionResponse.class)))
    @PostMapping("/sign-in")
    public final GlobalResponse<TokenInfoResponse> signIn(@RequestBody @Valid final SignInRequestBody requestBody) {
        Authentication authentication = authenticationHandler.getAuthentication(requestBody.getEmail(), requestBody.getPassword());

        return GlobalResponse.<TokenInfoResponse>builder()
            .message("로그인")
            .result(generateTokenUseCase.generateToken(authentication.getName()))
            .build();
    }

    @Operation(summary = "토큰 재발급", description = "리프레쉬 토큰으로 액세스 토큰 재발급",
        security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "401", description = "에러 발생",
        content = @Content(schema = @Schema(implementation = GlobalExceptionResponse.class)))
    @GetMapping("/reissue")
    public final GlobalResponse<TokenInfoResponse> reissue(HttpServletRequest request) {
        return GlobalResponse.<TokenInfoResponse>builder()
            .message("토큰 재발급")
            .result(reissueByRefreshTokenUseCase.reissue(
                resolveTokenUseCase.resolveToken(request.getHeader("Authorization"))))
            .build();
    }
}