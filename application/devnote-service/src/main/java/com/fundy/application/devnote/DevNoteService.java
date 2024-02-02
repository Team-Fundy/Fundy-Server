package com.fundy.application.devnote;

import com.fundy.application.devnote.in.DevNoteFindByIdUseCase;
import com.fundy.application.devnote.in.SaveDevNoteUseCase;
import com.fundy.application.devnote.in.dto.req.SaveDevNoteRequest;
import com.fundy.application.devnote.in.dto.res.DevNoteDetailResponse;
import com.fundy.application.devnote.out.LoadDevNotePort;
import com.fundy.application.devnote.out.SaveDevNotePort;
import com.fundy.application.devnote.out.command.SaveDevNoteCommand;
import com.fundy.application.exception.custom.NoInstanceException;
import com.fundy.domain.devnote.DevNote;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class DevNoteService implements DevNoteFindByIdUseCase, SaveDevNoteUseCase {
    //private final ValidDevNotePort validDevNotePort;
    private final LoadDevNotePort loadDevNotePort;
    private final SaveDevNotePort saveDevNotePort;


    @Override
    public DevNoteDetailResponse findById(Long id) {
        DevNote devNote = loadDevNotePort.findById(id).orElseThrow(() -> new NoInstanceException("해당 id의 개발노트가 존재하지 않음"));

        return DevNoteDetailResponse.builder()
                .id(devNote.getId())
                .title(devNote.getTitle())
                .content(devNote.getContent())
                .createdAt(devNote.getCreatedAt())
                .build();
    }

    @Override
    @Transactional
    public SaveDevNoteRequest saveDevNote(final SaveDevNoteRequest saveDevNoteRequest) {

        saveDevNotePort.saveDevNote(SaveDevNoteCommand.builder()
                        .title(saveDevNoteRequest.getTitle())
                        .content(saveDevNoteRequest.getContent())
                        .thumbnail(saveDevNoteRequest.getThumbnail())
                        .build());

        return SaveDevNoteRequest.builder()
                .title(saveDevNoteRequest.getTitle())
                .content(saveDevNoteRequest.getContent())
                .thumbnail(saveDevNoteRequest.getThumbnail())
                .build();
    }



}
