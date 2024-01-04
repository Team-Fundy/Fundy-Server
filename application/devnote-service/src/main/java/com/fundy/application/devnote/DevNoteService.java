package com.fundy.application.devnote;

import com.fundy.application.devnote.in.DevNoteFindByIdUseCase;
import com.fundy.application.devnote.in.dto.req.FindByIdRequest;
import com.fundy.application.devnote.in.dto.res.FindByIdResponse;
import com.fundy.application.devnote.out.LoadDevNotePort;
import com.fundy.application.devnote.out.ValidDevNotePort;
import com.fundy.domain.devnote.DevNote;
import com.fundy.jpa.devnote.adapter.DevNotePersistenceAdapter;
import com.fundy.jpa.devnote.repository.DevNoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class DevNoteService implements DevNoteFindByIdUseCase {
    private final ValidDevNotePort validDevNotePort;
    private final LoadDevNotePort loadDevNotePort;
    private final DevNotePersistenceAdapter devNotePersistenceAdapter;

    @Override
    public FindByIdResponse findById(Long id) {
        DevNote devNote = devNotePersistenceAdapter.findById(id).orElseThrow(() -> new RuntimeException("No DevNote"));

        return FindByIdResponse.builder()
                .title(devNote.getTitle())
                .contents(devNote.getContents())
                .build();
    }

}
