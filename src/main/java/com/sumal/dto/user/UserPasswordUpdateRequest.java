package com.sumal.dto.user;

public record UserPasswordUpdateRequest(String oldPassword, String newPassword) {}
