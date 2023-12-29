package com.fundy.jpa.devnote.adapter;

import com.fundy.application.devnote.out.LoadDevNotePort;
import com.fundy.application.devnote.out.ValidDevNotePort;
import com.fundy.domain.devnote.DevNote;
import com.fundy.jpa.devnote.mapper.DevNoteMapper;
import com.fundy.jpa.devnote.repository.DevNoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class DevNotePersistenceAdapter implements LoadDevNotePort, ValidDevNotePort {
//    private final DevNoteRepository devNoteRepository;
//    private final DevNoteMapper mapper;

    //ID로 조회
    @Override
    public Optional<DevNote> findById(String id) {
        return Optional.empty();
    }
    @Override
    public boolean existById(String id) {
        return true;
    }

}
