package com.fundy.jpa.user.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Table(name = "fundy_user")
@Getter
@DynamicInsert
@DynamicUpdate
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "email")
    private String email;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "creator_name")
    private String creatorName;

    @Column(name = "creator_description")
    private String creatorDescription;

    @Column(name = "creator_profile")
    private String creatorProfile;

    @Column(name = "creator_background")
    private String creatorBackground;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_authority", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    @Column(name = "authority")
    private List<String> authorities = new ArrayList<>();

    @Builder
    private UserModel(UUID id, String email, String nickname, String password, List<String> authorities) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.authorities = authorities;
    }
}