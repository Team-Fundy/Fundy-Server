package com.fundy.api.controller.devnote;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "DevNote", description = "개발노트 관련 API")
@RequestMapping("/devnote")
@RequiredArgsConstructor
@Slf4j
public class DevNoteController {

    //개발노트 조회

}
