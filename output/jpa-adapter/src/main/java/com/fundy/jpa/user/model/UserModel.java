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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Table(name = "fundy_user")
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@DynamicInsert
@DynamicUpdate
@ToString
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name = "password")
    private String password;

    @Column(name = "PROFILE")
    private String profile;

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
}