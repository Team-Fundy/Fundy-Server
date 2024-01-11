package com.fundy.api.controller.user;

import com.fundy.api.common.response.GlobalExceptionResponse;
import com.fundy.api.common.response.GlobalResponse;
import com.fundy.application.user.in.GetTokenizationUserInfoUseCase;
import com.fundy.application.user.in.IsAvailableNicknameUseCase;
import com.fundy.application.user.in.ResolveTokenUseCase;
import com.fundy.application.user.in.dto.res.IsAvailableNicknameResponse;
import com.fundy.application.user.in.dto.res.TokenizationUserInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "User", description = "유저 관련 API")
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final IsAvailableNicknameUseCase isAvailableNicknameUseCase;
    private final GetTokenizationUserInfoUseCase getTokenizationUserInfoUseCase;
    private final ResolveTokenUseCase resolveTokenUseCase;

    @Operation(summary = "닉네임 검증", description = "닉네임 사용가능한지 체크")
    @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "400", description = "에러 발생",
        content = @Content(schema = @Schema(implementation = GlobalExceptionResponse.class)))
    @GetMapping("/check-nickname")
    public final GlobalResponse<IsAvailableNicknameResponse> isAvailableNickname(
        @Parameter(description = "중복 검사할 닉네임", example = "유저-123")
        @RequestParam(name = "nickname") String nickname) {
        return GlobalResponse.<IsAvailableNicknameResponse>builder()
            .message("닉네임 사용 가능 여부 확인")
            .result(isAvailableNicknameUseCase.isAvailableNickname(nickname))
            .build();
    }

    @Operation(summary = "유저 정보 조회", description = "토큰으로 유저 정보 조회",
        security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "400", description = "에러 발생",
        content = @Content(schema = @Schema(implementation = GlobalExceptionResponse.class)))
    @ApiResponse(responseCode = "401", description = "토큰 문제",
        content = @Content(schema = @Schema(implementation = GlobalExceptionResponse.class)))
    @GetMapping("/info")
    public final GlobalResponse<TokenizationUserInfoResponse> getUserInfoByToken(HttpServletRequest request) {
        return GlobalResponse.<TokenizationUserInfoResponse>builder()
            .message("토큰으로 유저 정보 조회")
            .result(getTokenizationUserInfoUseCase.getTokenizationUserInfoByAccessToken(
                resolveTokenUseCase.resolveToken(request.getHeader("Authorization"))))
            .build();
    }
}