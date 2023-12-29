package com.fundy.jpa.devnote.mapper;

import com.fundy.domain.devnote.DevNote;
import com.fundy.jpa.devnote.model.DevNoteModel;

//일단 따라함 수정 필요
public class DevNoteMapper {
    public final DevNoteModel domainToEntity(DevNote devNote) {
        return DevNoteModel.builder().build();
    }
    public final DevNote entityToDomain(DevNoteModel model) { return null; }
}
