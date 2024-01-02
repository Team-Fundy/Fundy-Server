package com.fundy.application.user.in;

import com.fundy.application.user.in.dto.res.IsAvailableEmailResponse;

public interface IsAvailableEmailUseCase {
    IsAvailableEmailResponse isAvailableEmail(String email);
}
