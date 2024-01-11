package com.fundy.redis.refresh.mapper;

import com.fundy.domain.user.User;
import com.fundy.domain.user.enums.Authority;
import com.fundy.domain.user.interfaces.TokenizationUser;
import com.fundy.domain.user.vos.Email;
import com.fundy.domain.user.vos.Image;
import com.fundy.domain.user.vos.Nickname;
import com.fundy.redis.refresh.model.RefreshInfoModel;
import org.springframework.stereotype.Component;

@Component
public class RefreshMapper {
    public final TokenizationUser entityToDomain(RefreshInfoModel model) {
        if (model == null)
            return null;

        return User.builder()
            .email(Email.of(model.getId()))
            .nickname(Nickname.of(model.getNickname()))
            .profile(Image.of(model.getProfile()))
            .authorities(model.getAuthorities().stream().map(Authority::valueOf).toList())
            .build();
    }
}
