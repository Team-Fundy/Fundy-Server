package com.fundy.jpa.user.mapper;

import com.fundy.domain.user.User;
import com.fundy.domain.user.enums.Authority;
import com.fundy.domain.user.vos.CreatorInfo;
import com.fundy.domain.user.vos.Email;
import com.fundy.domain.user.vos.Image;
import com.fundy.domain.user.vos.Password;
import com.fundy.domain.user.vos.Phone;
import com.fundy.domain.user.vos.UserId;
import com.fundy.jpa.user.model.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User modelToDomain(UserModel model) {
        User.UserBuilder userBuilder = User.builder();

        userBuilder
            .id(UserId.of(model.getId()))
            .email(Email.of(model.getEmail()))
            .nickname(model.getNickname())
            .password(Password.of(model.getPassword()))
            .profile(Image.newInstance(model.getProfile()))
            .authorities(model.getAuthorities().stream()
                .map(Authority::valueOf).toList());

        // null 일 수 있는 항목
        if (model.getPhone() != null)
            userBuilder.phone(Phone.of(model.getPhone()));

        CreatorInfo creatorInfo = CreatorInfo.builder()
            .name(model.getCreatorName())
            .description(model.getCreatorDescription())
            .profile(Image.newInstance(model.getCreatorProfile()))
            .background(Image.newInstance(model.getCreatorBackground()))
            .build();

        userBuilder.creatorInfo(creatorInfo);
        // TODO: deliveryAddress 추가

        return userBuilder.build();
    }
}
