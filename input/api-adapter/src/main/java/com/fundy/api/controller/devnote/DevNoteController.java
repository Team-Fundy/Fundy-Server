package com.fundy.api.controller.devnote;

import com.fundy.api.common.response.GlobalExceptionResponse;
import com.fundy.api.common.response.GlobalResponse;
import com.fundy.application.devnote.in.DevNoteFindByIdUseCase;
import com.fundy.application.devnote.in.dto.res.DevNoteDetailResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "DevNote", description = "개발노트 관련 API")
@RequestMapping("/devnote")
@RequiredArgsConstructor
@Slf4j
public class DevNoteController {
    private final DevNoteFindByIdUseCase devNoteFindByIdUseCase;

    @Operation(summary = "개발노트 id로 조회", description = "개발노트의 id로 개발노트 상세 정보를 조회함")
    @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "400", description = "에러 발생",
            content = @Content(schema = @Schema(implementation = GlobalExceptionResponse.class)))
    @GetMapping("/findbyid/{id}")
    public final GlobalResponse<DevNoteDetailResponse> getDevNoteById(@PathVariable(name = "id") Long id) {
        return GlobalResponse.<DevNoteDetailResponse>builder()
                .message("개발노트 상세 조회")
                .result(devNoteFindByIdUseCase.findById(id))
                .build();
    }

}
