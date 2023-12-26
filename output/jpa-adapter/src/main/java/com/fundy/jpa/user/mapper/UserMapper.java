package com.fundy.jpa.user.mapper;

import com.fundy.domain.user.User;
import com.fundy.jpa.user.model.UserModel;
import org.springframework.stereotype.Component;

// TODO: 여기 고쳐야함
@Component
public class UserMapper {
    public final UserModel domainToEntity(User user) {
        return UserModel.builder()
            .build();
    }

    public final User entityToDomain(UserModel model) {
        return null;
    }
}
