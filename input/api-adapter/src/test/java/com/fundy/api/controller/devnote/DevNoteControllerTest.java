package com.fundy.api.controller.devnote;

import com.fundy.api.BaseIntegrationTest;
import com.fundy.application.devnote.out.LoadDevNotePort;
import com.fundy.domain.devnote.DevNote;
import com.fundy.domain.devnote.vos.DevNoteId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
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
        DevNoteId devNoteId = DevNoteId.of(1L);
        LocalDateTime createdAt = LocalDateTime.MIN;

        given(loadDevNotePort.findById(id)).willReturn(Optional.of(DevNote.builder()
                .id(devNoteId)
                .title(title)
                .content(content)
                .createdAt(createdAt)
                .build()));


        //when
        ResultActions resultActions = mvc.perform(get("/devnote/findbyid/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                //.queryParam("id", String.valueOf(id))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
        //when
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.result.title").value(title));
        resultActions.andExpect(jsonPath("$.result.content").value(content));
        resultActions.andExpect(jsonPath("$.result.devNoteId").value(devNoteId));
        resultActions.andExpect(jsonPath("$.result.createdAt").value(createdAt));


    }

    @DisplayName("[실패] 개발노트 조회 : 해당 아이디 개발노트 없음")
    @Test
    void findByIdFailCaseWithExist() throws Exception{
        //given
        Long id = 1L;
        String title = "Test title";
        String content = "Test content";
        DevNoteId devNoteId = DevNoteId.of(1L);
        LocalDateTime createdAt = LocalDateTime.MIN;

        given(loadDevNotePort.findById(id)).willReturn(Optional.of(DevNote.builder()
                .id(null)
                .title(null)
                .content(null)
                .createdAt(null)
                .build()));

        //when
        ResultActions resultActions = mvc.perform(get("/devnote/findbyid/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        //.queryParam("id", String.valueOf(id))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.result.title").isEmpty());
        resultActions.andExpect(jsonPath("$.result.content").isEmpty());
        resultActions.andExpect(jsonPath("$.result.devNoteId").isEmpty());
        resultActions.andExpect(jsonPath("$.result.createdAt").isEmpty());
    }

}