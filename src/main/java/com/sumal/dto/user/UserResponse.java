package com.sumal.dto.user;

import com.sumal.common.Role;
import java.util.List;

public record UserResponse(
    String id,
    String username,
    String firstName,
    String lastName,
    List<Role> roles,
    Boolean active) {}
