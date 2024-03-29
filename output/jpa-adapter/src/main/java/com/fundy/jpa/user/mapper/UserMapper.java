package com.fundy.jpa.user.mapper;

import com.fundy.domain.user.User;
import com.fundy.domain.user.enums.Authority;
import com.fundy.domain.user.vos.CreatorInfo;
import com.fundy.domain.user.vos.Email;
import com.fundy.domain.user.vos.Image;
import com.fundy.domain.user.vos.Nickname;
import com.fundy.domain.user.vos.Password;
import com.fundy.domain.user.vos.Phone;
import com.fundy.domain.user.vos.UserId;
import com.fundy.jpa.user.model.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User modelToDomain(UserModel model) {
        if (model == null)
            return null;

        User.UserBuilder userBuilder = User.builder();

        userBuilder
            .id(UserId.of(model.getId()))
            .email(Email.of(model.getEmail()))
            .nickname(Nickname.of(model.getNickname()))
            .profile(Image.of(model.getProfile()))
            .authorities(model.getAuthorities().stream()
                .map(Authority::valueOf).toList());

        // null 일 수 있는 항목
        if (model.getPhone() != null)
            userBuilder.phone(Phone.of(model.getPhone()));

        if (model.getPassword() != null)
            userBuilder.password(Password.of(model.getPassword()));

        CreatorInfo creatorInfo = CreatorInfo.builder()
            .name(model.getCreatorName())
            .description(model.getCreatorDescription())
            .profile(Image.of(model.getCreatorProfile()))
            .background(Image.of(model.getCreatorBackground()))
            .build();

        userBuilder.creatorInfo(creatorInfo);
        // TODO: deliveryAddress 추가

        return userBuilder.build();
    }
}
