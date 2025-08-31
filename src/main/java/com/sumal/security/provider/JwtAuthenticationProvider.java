package com.sumal.security.provider;

import com.sumal.security.authentication.JwtAuthentication;
import com.sumal.security.user.AuthUser;
import com.sumal.service.JwtService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {


    private final JwtService jwtService;


    public JwtAuthenticationProvider(JwtService jwtService) {
        this.jwtService = jwtService;
    }


    @Override
    public Authentication authenticate(Authentication authentication) {


        JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication;


        AuthUser authUser = jwtService.resolveJwtToken(jwtAuthentication.jwtToken());


        return JwtAuthentication.authenticated(authUser);
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.isAssignableFrom(authentication);
    }
}
