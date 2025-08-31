package com.sumal.security.user;

import com.sumal.common.AuthUserType;
import com.sumal.common.Role;

import java.util.List;

public record AuthUser(String userId, List<Role> roles, AuthUserType authUserType) {

    public boolean isInternalUser() {
        return authUserType == AuthUserType.INTERNAL;
    }
}
