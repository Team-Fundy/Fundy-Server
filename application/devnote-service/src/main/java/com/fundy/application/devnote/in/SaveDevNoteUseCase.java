package com.fundy.application.devnote.in;

import com.fundy.application.devnote.in.dto.req.SaveDevNoteRequest;

public interface SaveDevNoteUseCase {
    SaveDevNoteRequest saveDevNote(final SaveDevNoteRequest saveDevNoteRequest);
}
