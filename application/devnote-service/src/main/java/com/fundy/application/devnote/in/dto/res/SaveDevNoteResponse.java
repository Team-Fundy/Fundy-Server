package com.fundy.application.devnote.in.dto.res;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class SaveDevNoteResponse {
    private Long id;
    private String title;
}
