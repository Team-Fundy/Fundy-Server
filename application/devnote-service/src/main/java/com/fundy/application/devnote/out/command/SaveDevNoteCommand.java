package com.fundy.application.devnote.out.command;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SaveDevNoteCommand {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
}
