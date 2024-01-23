package com.fundy.application.devnote;

import com.fundy.application.devnote.in.dto.res.DevNoteDetailResponse;
import com.fundy.application.devnote.out.LoadDevNotePort;
import com.fundy.application.exception.custom.NoInstanceException;
import com.fundy.domain.devnote.DevNote;
import com.fundy.domain.devnote.vos.DevNoteId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("DevNoteService 유닛 테스트")
class DevNoteServiceTest {
    @InjectMocks
    private DevNoteService devNoteService;

    @Mock
    private LoadDevNotePort loadDevNotePort;


    @DisplayName("[성공] 아이디로 개발노트 조회")
    @Test
    void findByIdSuccessCase() {

        //given
        Long id = 1L;
        DevNoteId devNoteId = DevNoteId.of(1L);
        String title = "Test title";
        String content = "Test content";
        LocalDateTime createdAt = LocalDateTime.MIN;
        given(loadDevNotePort.findById(id)).willReturn(Optional.of(DevNote.builder()
                        .id(devNoteId)
                        .title(title)
                        .content(content)
                        .createdAt(createdAt)
                        .build()));

        //when
        DevNoteDetailResponse result = devNoteService.findById(id);


        //then
        Assertions.assertThat(result.getId()).isEqualTo(devNoteId);
        Assertions.assertThat(result.getTitle()).isEqualTo(title);
        Assertions.assertThat(result.getContent()).isEqualTo(content);
        verify(loadDevNotePort, times(1)).findById(any());
    }

    @DisplayName("[실패] 개발노트 조회 : 해당 아이디 개발노트 없음")
    @Test
    void findByIdFailCaseWithExist() {
        //given
        Long id = 1L;
        given(loadDevNotePort.findById(id)).willReturn(Optional.empty());

        //when, then
        Assertions.assertThatThrownBy(()-> devNoteService.findById(id)).isInstanceOf(NoInstanceException.class);
        verify(loadDevNotePort, times(1)).findById(any());
    }

}