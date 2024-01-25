package com.fundy.application.devnote.out.command;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SaveDevNoteCommand {
    private String title;
    private String content;
    private String thumbnail;
}
