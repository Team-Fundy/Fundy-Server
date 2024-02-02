package com.fundy.application.devnote.in.dto;

import com.fundy.application.devnote.in.dto.res.DevNoteListResponse;

public interface DevNoteListFindById {
    DevNoteListResponse listFindById(Long id);
}
