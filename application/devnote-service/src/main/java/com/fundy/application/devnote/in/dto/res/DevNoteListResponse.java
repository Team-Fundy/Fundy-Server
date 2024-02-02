package com.fundy.application.devnote.in.dto.res;


import com.fundy.domain.devnote.DevNote;
import lombok.*;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class DevNoteListResponse {
    private List<DevNote> devNoteList;

}
