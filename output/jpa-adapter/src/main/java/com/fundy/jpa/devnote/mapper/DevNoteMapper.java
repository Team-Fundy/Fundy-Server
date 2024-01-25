package com.fundy.jpa.devnote.mapper;

import com.fundy.domain.devnote.DevNote;
import com.fundy.domain.devnote.vos.DevNoteId;
import com.fundy.domain.devnote.vos.Image;
import com.fundy.jpa.devnote.model.DevNoteModel;
import org.springframework.stereotype.Component;

//TODO : 수정이 필요함
//FIXME : Mapper의 역할에 맞게 추가
@Component
public class DevNoteMapper {
    public final DevNoteModel domainToEntity(DevNote devNote) {
        return DevNoteModel.builder().build();
    }

    public final DevNote entityToDomain(DevNoteModel model) {
        if (model == null)
            return null;

        return DevNote.builder()
                .id(DevNoteId.of(model.getId()))
                .title(model.getTitle())
                .content(model.getContent())
                .thumbnail(Image.of(model.getThumbnail()))
                .createdAt(model.getCreatedAt())
                .build();
    }
}
