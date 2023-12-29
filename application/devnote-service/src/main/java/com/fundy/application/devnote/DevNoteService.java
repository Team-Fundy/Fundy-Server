package com.fundy.application.devnote;

import com.fundy.application.devnote.in.DevNoteFindByIdUseCase;
import com.fundy.application.devnote.in.dto.req.FindByIdRequest;
import com.fundy.application.devnote.in.dto.res.FindByIdResponse;
import com.fundy.application.devnote.out.LoadDevNotePort;
import com.fundy.application.devnote.out.ValidDevNotePort;
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

    @Transactional
    @Override
    public FindByIdResponse findById(final FindByIdRequest findByIdRequest) {

        return FindByIdResponse.builder()
                .title(findByIdRequest.getTitle())
                .contents(findByIdRequest.getContents())
                .projectId(findByIdRequest.getProjectId())
                .build();

    }

}
