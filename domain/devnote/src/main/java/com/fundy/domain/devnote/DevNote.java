package com.fundy.domain.devnote;

import com.fundy.domain.devnote.enums.ProjectType;
import com.fundy.domain.devnote.vos.DevNoteId;
import com.fundy.domain.devnote.vos.Image;
import com.fundy.domain.devnote.vos.ProjectId;
import lombok.Getter;

import java.time.LocalDateTime;

public class DevNote {
    @Getter
    private DevNoteId id;
    @Getter
    private String contents;
    @Getter
    private String title;
    @Getter
    private LocalDateTime createdAt;
    @Getter
    private ProjectType projectType;
    @Getter
    private ProjectId projectId;
    @Getter
    private Image thumbnail;
    
    //이거 지워
//    public DevNote(LocalDateTime createdAt) {
//        this.createdAt = createdAt;
//    }

    //일반 사용자들이 오픈 가능한지 아닌지
    public boolean isOpen(LocalDateTime nextUploadDateTime) {
        return nextUploadDateTime.isBefore(createdAt);
    }
    

}
