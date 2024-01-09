package com.fundy.domain.devnote.vos;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class DevNoteId {
    private Long id;

    public static DevNoteId of (Long id){ return new DevNoteId(id); }



}
