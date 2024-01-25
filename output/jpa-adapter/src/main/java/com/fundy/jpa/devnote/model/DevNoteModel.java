package com.fundy.jpa.devnote.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "devnote")
@Getter
@DynamicInsert
@DynamicUpdate
public class DevNoteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;
    @Column(name = "thumbnail")
    private String thumbnail;

    //TODO : 더 많은 내용이 필요할 수 있음
    //FIXME : 개발노트에 필요한 도메인 추가
    @Builder
    private DevNoteModel(String title, String content, String thumbnail) {
        this.thumbnail = thumbnail;
        this.title = title;
        this.content = content;
    }





}
