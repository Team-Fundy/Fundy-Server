package com.fundy.application.devnote.in.dto.req;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class SaveDevNoteRequest {
    private String title;
    private String content;
    private String thumbnail;
}
