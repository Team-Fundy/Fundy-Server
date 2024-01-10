package com.fundy.application.devnote;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("DevNoteService 유닛 테스트")
class DevNoteServiceTest {
    @InjectMocks
    private DevNoteService devNoteService;

    @DisplayName("[성공] 아이디로 개발노트 찾기")
    @Test
    void findByIdSuccessCase() {

        //given
        Long id = 1L;

        //when

        //then
    }

    @DisplayName("[실패] 개발노트 조회 : 해당 개발노트 없음")
    @Test
    void findByIdFailCaseWithExist() {
        //given

        //when

        //then
    }

}