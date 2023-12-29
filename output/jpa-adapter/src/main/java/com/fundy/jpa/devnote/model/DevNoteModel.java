package com.fundy.jpa.devnote.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.UUID;

@Entity
@NoArgsConstructor
@Table(name = "fundy_devnote")
@Getter
@DynamicInsert
@DynamicUpdate
public class DevNoteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "nickname")
    private String nickname;
    @Column(name = "title")
    private String title;
    @Column(name = "contents")
    private String contents;


    @Builder
    private DevNoteModel(UUID id, String nickname, String title, String contents) {
        this.id = id;
        this.nickname = nickname;
        this.title = title;
        this.contents = contents;
    }





}
