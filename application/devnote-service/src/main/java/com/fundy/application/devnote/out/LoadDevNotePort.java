package com.fundy.application.devnote.out;

import com.fundy.domain.devnote.DevNote;

import java.util.Optional;

public interface LoadDevNotePort {
    Optional<DevNote> findById(String id);
}
