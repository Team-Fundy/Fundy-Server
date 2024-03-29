package com.fundy.application.devnote.in.dto.res;

import com.fundy.domain.devnote.vos.DevNoteId;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class DevNoteDetailResponse {
    private DevNoteId id;
    private String title;
    private String content;
    private LocalDateTime createdAt;

}
