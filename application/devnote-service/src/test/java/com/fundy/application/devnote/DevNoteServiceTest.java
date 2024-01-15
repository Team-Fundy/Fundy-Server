package com.fundy.application.devnote;

import com.fundy.application.devnote.in.dto.res.DevNoteDetailResponse;
import com.fundy.application.devnote.out.LoadDevNotePort;
import com.fundy.application.devnote.out.ValidDevNotePort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("DevNoteService 유닛 테스트")
class DevNoteServiceTest {
    @InjectMocks
    private DevNoteService devNoteService;

    @Mock
    private LoadDevNotePort loadDevNotePort;
    
    @Mock
    private ValidDevNotePort validDevNotePort;

    @DisplayName("[성공] 아이디로 개발노트 찾기")
    @Test
    void findByIdSuccessCase() {

        //given
        Long id = 1L;
        String title = "Sample Title";
        String contents = "Sample Text";

        //when
        DevNoteDetailResponse result = devNoteService.findById(id);


        //then
        Assertions.assertThat(result.getTitle()).isEqualTo(title);
        Assertions.assertThat(result.getContents()).isEqualTo(contents);
        verify(loadDevNotePort, times(1)).findById(any());
    }

    @DisplayName("[실패] 개발노트 조회 : 해당 아이디 개발노트 없음")
    @Test
    void findByIdFailCaseWithExist() {
        //given
        Long id = 1L;

        //when
        DevNoteDetailResponse result = devNoteService.findById(id);

        //then
    }

}