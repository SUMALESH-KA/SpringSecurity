package com.sumal.service;


import java.util.UUID;

import com.sumal.dto.user.UserResponse;
import com.sumal.dto.user.UserResponseWithCredentials;
import com.sumal.security.dto.LoginDto;
import com.sumal.security.dto.TokenDto;
import com.sumal.security.exception.ApplicationAuthenticationException;
import com.sumal.security.user.AuthUser;
import com.sumal.security.user.AuthUserCache;
import com.sumal.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  private final AuthUserCache authUserCache;

  private final UserService userService;

  private final PasswordEncoder passwordEncoder;

  public AuthService(
      AuthUserCache authUserCache, UserService userService, PasswordEncoder passwordEncoder) {
    this.authUserCache = authUserCache;
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  public TokenDto login(LoginDto loginDto) {

    boolean isActive = userService.validateActive(loginDto.username());

    if(!isActive) {
      throw new ApplicationAuthenticationException("User is not active");
    }
    UserResponseWithCredentials userCredentials =
        userService.getUserCredentialsByUsername(loginDto.username());

    if (!passwordEncoder.matches(loginDto.password(), userCredentials.passwordHash())) {
      throw new ApplicationAuthenticationException("Password is incorrect");
    }

    String token = UUID.randomUUID().toString();
    UserResponse userResponse = userCredentials.userResponse();

    AuthUser authUser = new AuthUser(userResponse.id(), userResponse.roles());

    authUserCache.login(token, authUser);

    return new TokenDto(token);
  }

  public void logout(String token) {

    authUserCache.logout(token);
  }
}
