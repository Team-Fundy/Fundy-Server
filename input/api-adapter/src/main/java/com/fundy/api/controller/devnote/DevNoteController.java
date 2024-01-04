package com.fundy.api.controller.devnote;

import com.fundy.application.devnote.DevNoteService;
import com.fundy.application.devnote.in.dto.res.FindByIdResponse;
import com.fundy.domain.devnote.DevNote;
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
    private final DevNoteService devNoteService;
    //개발노트 조회
    @GetMapping("/{id}")
    public final FindByIdResponse getDevNoteById(@PathVariable(name = "id") Long id) {
        return devNoteService.findById(id);
    }

}
