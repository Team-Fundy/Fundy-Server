package com.fundy.api.controller.devnote;

import com.fundy.api.BaseIntegrationTest;
import com.fundy.application.devnote.out.LoadDevNotePort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

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
//        DevNoteId devNoteId = DevNoteId.of(1L);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
//        LocalDateTime createdAt = LocalDateTime.parse("2024-01-23T15:57:53", formatter);
        String createdAt = "2024-01-23T15:57:53";
        //2024-01-23 15:57:53
//        given(loadDevNotePort.findById(id)).willReturn(Optional.of(DevNote.builder()
//                .id(devNoteId)
//                .title(title)
//                .content(content)
//                .createdAt(createdAt)
//                .build()));


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
        resultActions.andExpect(jsonPath("$.result.createdAt").value(createdAt));


    }

    @DisplayName("[실패] 개발노트 조회 : 해당 아이디 개발노트 없음")
    @Test
    void findByIdFailCaseWithExist() throws Exception{
        //given
        Long id = 30L;
//        String title = "Test title";
//        String content = "Test content";
//        DevNoteId devNoteId = DevNoteId.of(1L);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime createdAt = LocalDateTime.parse("2024-01-23 15:57:53", formatter);

//        given(loadDevNotePort.findById(id)).willReturn(Optional.of(DevNote.builder()
//                .id(null)
//                .title(null)
//                .content(null)
//                .createdAt(null)
//                .build()));

        //when
        ResultActions resultActions = mvc.perform(get("/devnote/findbyid/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        //.queryParam("id", String.valueOf(id))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        //resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.result.title").doesNotExist());
        resultActions.andExpect(jsonPath("$.result.content").doesNotExist());
        //resultActions.andExpect(jsonPath("$.result.devNoteId").doesNotExist());
        resultActions.andExpect(jsonPath("$.result.createdAt").doesNotExist());
    }

}