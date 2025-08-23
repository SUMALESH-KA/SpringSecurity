package com.sumal.dto.user;

public record UserCreateRequest(
    String username, String password, String firstName, String lastName) {}
