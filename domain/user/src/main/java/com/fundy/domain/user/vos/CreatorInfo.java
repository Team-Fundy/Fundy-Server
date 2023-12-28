package com.fundy.domain.user.vos;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreatorInfo {
    private String name;
    private String description;
    private Image profile;
    private Image background;

    @Builder
    private CreatorInfo(String name, String description, Image profile, Image background) {
        this.name = Optional.ofNullable(name).orElse("크리에이터");
        this.description = description;
        this.profile = profile;
        this.background = background;
    }

    public static boolean validateName(String name) {
        return name.length() >= 2 && name.length() <= 30;
    }

    public static boolean validateDescription(String description) {
        return description.length() >= 2 && description.length() <= 250;
    }
}