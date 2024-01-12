package com.fundy.api.controller.auth.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenRequestBody {
    @NotNull(message = "리프레쉬 토큰은 필수 입니다.")
    @Schema(description = "리프레쉬 토큰", example = "fklajkdffjalkjl2j30fdaklfjk")
    private String refreshToken;
}
