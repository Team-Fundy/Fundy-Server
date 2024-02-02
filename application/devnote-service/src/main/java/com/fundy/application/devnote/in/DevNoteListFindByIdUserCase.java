package com.fundy.application.devnote.in;

import com.fundy.application.devnote.in.dto.res.DevNoteListResponse;

public interface DevNoteListFindByIdUserCase {
    DevNoteListResponse listFindById(Long id);
}
