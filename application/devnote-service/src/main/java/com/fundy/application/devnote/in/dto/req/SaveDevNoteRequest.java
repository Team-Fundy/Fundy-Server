package com.fundy.application.devnote.in.dto.req;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class SaveDevNoteRequest {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
}
