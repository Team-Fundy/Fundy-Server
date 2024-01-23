package com.fundy.application.devnote.in;

import com.fundy.application.devnote.in.dto.req.SaveDevNoteRequest;
import com.fundy.application.devnote.in.dto.res.SaveDevNoteResponse;

public interface SaveDevNoteUseCase {
    SaveDevNoteResponse saveDevNote(final SaveDevNoteRequest saveDevNoteRequest);
}
