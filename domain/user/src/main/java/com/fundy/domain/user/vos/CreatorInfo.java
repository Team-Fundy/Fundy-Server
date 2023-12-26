package com.fundy.domain.user.vos;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreatorInfo {
    private String name;
    @Getter
    private String description;
    @Getter
    private Image profile;
    @Getter
    private Image background;

    public static CreatorInfo newInstance() {
        return new CreatorInfo();
    }

    public static CreatorInfo of(String name, String description, Image profile, Image background) {
        return new CreatorInfo(name,description,profile,background);
    }

    public static boolean validateName(String name) {
        return name.length() >= 2 && name.length() <= 30;
    }

    public static boolean validateDescription(String description) {
        return description.length() >= 2 && description.length() <= 250;
    }

    public String getName() {
        if (name == null)
            return "크리에이터";
        return name;
    }
}