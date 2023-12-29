package com.fundy.jpa.devnote.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface DevNoteRepository {
    boolean existById(Long id);
}
