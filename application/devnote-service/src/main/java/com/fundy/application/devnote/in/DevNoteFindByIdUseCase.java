package com.fundy.application.devnote.in;

import com.fundy.application.devnote.in.dto.res.DevNoteDetailResponse;

public interface DevNoteFindByIdUseCase {
    DevNoteDetailResponse findById(Long id);
}
