package com.fundy.api.controller.devnote;

import com.fundy.api.common.response.GlobalExceptionResponse;
import com.fundy.api.common.response.GlobalResponse;
import com.fundy.application.devnote.in.DevNoteFindByIdUseCase;
import com.fundy.application.devnote.in.DevNoteListFindByIdUserCase;
import com.fundy.application.devnote.in.SaveDevNoteUseCase;
import com.fundy.application.devnote.in.dto.req.SaveDevNoteRequest;
import com.fundy.application.devnote.in.dto.res.DevNoteDetailResponse;
import com.fundy.application.devnote.in.dto.res.DevNoteListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "DevNote", description = "개발노트 관련 API")
@RequestMapping("/devnote")
@RequiredArgsConstructor
@Slf4j
public class DevNoteController {
    private final SaveDevNoteUseCase saveDevNoteUseCase;
    private final DevNoteFindByIdUseCase devNoteFindByIdUseCase;
    private final DevNoteListFindByIdUserCase devNoteListFindByIdUserCase;



    @Operation(summary = "개발노트 저장", description = "개발노트 업로드")
    @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "400", description = "에러 발생",
            content = @Content(schema = @Schema(implementation = GlobalExceptionResponse.class)))
    @PostMapping
    public final GlobalResponse<SaveDevNoteRequest> saveDevNote(@RequestBody SaveDevNoteRequest request) {
        return GlobalResponse.<SaveDevNoteRequest>builder()
                .message("개발노트 저장")
                .result(saveDevNoteUseCase.saveDevNote(SaveDevNoteRequest.builder()
                                .title(request.getTitle())
                                .content(request.getContent())
                                .thumbnail(request.getThumbnail())
                        .build()))
                .build();
    }

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

    @Operation(summary = "개발노트 리스트 조회", description = "프로젝트 id에 해당하는 개발노트들 리스트로 조회함")
    @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "400", description = "에러 발생",
            content = @Content(schema = @Schema(implementation = GlobalExceptionResponse.class)))
    @GetMapping("/findlist/{id}")
    public final GlobalResponse<DevNoteListResponse> getDevNoteListById(@PathVariable(name = "id") Long id) {
        return GlobalResponse.<DevNoteListResponse>builder()
                .message("개발노트 리스트 조회")
                .result(devNoteListFindByIdUserCase.listFindById(id))
                .build();
    }

}
