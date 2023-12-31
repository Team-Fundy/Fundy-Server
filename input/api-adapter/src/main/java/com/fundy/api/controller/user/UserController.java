package com.fundy.api.controller.user;

import com.fundy.api.common.response.GlobalResponse;
import com.fundy.application.user.in.IsAvailableNicknameUseCase;
import com.fundy.application.user.in.dto.res.IsAvailableNicknameResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @GetMapping("/check-nickname")
    public final GlobalResponse<IsAvailableNicknameResponse> isAvailableNickname(
        @Parameter(description = "중복 검사할 닉네임", example = "유저-123")
        @RequestParam(name = "nickname") String nickname) {
        return GlobalResponse.<IsAvailableNicknameResponse>builder()
            .message("닉네임 사용 가능 여부 확인")
            .result(isAvailableNicknameUseCase.isAvailableNickname(nickname))
            .build();
    }
}