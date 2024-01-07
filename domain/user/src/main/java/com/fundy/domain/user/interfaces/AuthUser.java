package com.fundy.domain.user.interfaces;

import java.util.List;

public interface AuthUser {
    String getEmailAddress();
    List<String> getAuthorities();
}
