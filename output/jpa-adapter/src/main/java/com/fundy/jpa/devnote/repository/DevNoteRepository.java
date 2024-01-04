package com.fundy.jpa.devnote.repository;

import com.fundy.domain.devnote.DevNote;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DevNoteRepository {
    boolean existById(Long id);
}
