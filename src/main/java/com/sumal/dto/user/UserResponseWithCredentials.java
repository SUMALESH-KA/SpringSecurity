package com.sumal.dto.user;

public record UserResponseWithCredentials(UserResponse userResponse, String passwordHash) {}
