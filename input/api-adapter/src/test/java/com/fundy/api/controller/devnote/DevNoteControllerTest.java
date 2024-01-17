package com.fundy.api.controller.devnote;

import com.fundy.api.BaseIntegrationTest;
import com.fundy.application.devnote.out.LoadDevNotePort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("개발노트 통합 테스트")
class DevNoteControllerTest extends BaseIntegrationTest {

    @Autowired
    private LoadDevNotePort loadDevNotePort;

    @DisplayName("[성공] 아이디로 개발노트 조회")
    @Test
    void findByIdSuccessCase() throws Exception {
        //given
        Long id = 1L;
        String title = "Test title";
        String content = "Test content";

        //when
        ResultActions resultActions = mvc.perform(get("/devnote/findbyid")
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("id", String.valueOf(id))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
        //when
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.result.title").value(title));
        resultActions.andExpect(jsonPath("$.result.content").value(content));


    }


}