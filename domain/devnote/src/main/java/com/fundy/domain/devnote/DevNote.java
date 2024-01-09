package com.fundy.domain.devnote;

import com.fundy.domain.devnote.enums.ProjectType;
import com.fundy.domain.devnote.vos.DevNoteId;
import com.fundy.domain.devnote.vos.Image;
import com.fundy.domain.devnote.vos.ProjectId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class DevNote {

    private DevNoteId id;

    private String contents;

    private String title;

    private LocalDateTime createdAt;

    private ProjectType projectType;

    private ProjectId projectId;

    private Image thumbnail;
    

    //일반 사용자들이 오픈 가능한지 아닌지
    public boolean isOpen(LocalDateTime nextUploadDateTime) {
        return nextUploadDateTime.isBefore(createdAt);
    }
    

}
