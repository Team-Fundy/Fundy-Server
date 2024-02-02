package com.fundy.jpa.devnote.adapter;

import com.fundy.application.devnote.out.LoadDevNoteListPort;
import com.fundy.application.devnote.out.LoadDevNotePort;
import com.fundy.application.devnote.out.SaveDevNotePort;
import com.fundy.application.devnote.out.ValidDevNotePort;
import com.fundy.application.devnote.out.command.SaveDevNoteCommand;
import com.fundy.domain.devnote.DevNote;
import com.fundy.jpa.devnote.mapper.DevNoteMapper;
import com.fundy.jpa.devnote.model.DevNoteModel;
import com.fundy.jpa.devnote.repository.DevNoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class DevNotePersistenceAdapter implements LoadDevNotePort, ValidDevNotePort, SaveDevNotePort, LoadDevNoteListPort {
    private final DevNoteRepository devNoteRepository;
    private final DevNoteMapper mapper;

    //ID로 조회
    @Override
    public Optional<DevNote> findById(Long id) {
        // 도메인 모델 vs 영속성 모델
        /*
        * 도메인 모델: 도메인 로직을 가지고 있는 주요 모델 (DevNote)
        * 영속성 모델: DB와 데이터를 주고받기 위한 객체 모델 (DevNoteModel)
        * mapper: 도메인 모델 <-> 영속성 모델
        * */
        return Optional.ofNullable(mapper.entityToDomain(devNoteRepository.findById(id).orElse(null)));
    }
    @Override
    public boolean existById(Long id) {
        return devNoteRepository.existsById(id);
    }

    @Override
    public Long saveDevNote(SaveDevNoteCommand command) {
        return devNoteRepository.save(DevNoteModel.builder()
                        .title(command.getTitle())
                        .content(command.getContent())
                        .thumbnail(command.getThumbnail())
                    .build()).getId();
    }

    //FIXME : 구현 필요
    @Override
    public List<DevNote> listFindById(Long id) {

        List<DevNote> devNoteList = new ArrayList<>();
//        devNoteList = devNoteRepository.findById(id);
        return devNoteList;
    }


}
