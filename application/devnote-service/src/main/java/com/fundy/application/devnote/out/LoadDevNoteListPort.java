package com.fundy.application.devnote.out;

import com.fundy.domain.devnote.DevNote;

import java.util.List;

public interface LoadDevNoteListPort {
    List<DevNote> listFindById(Long id);
}
