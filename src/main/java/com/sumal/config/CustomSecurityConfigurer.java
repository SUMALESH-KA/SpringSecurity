package com.sumal.config;

import com.sumal.security.filter.ApiKeyFilter;
import com.sumal.security.filter.JwtTokenFilter;
import com.sumal.security.filter.SecurityAuthenticationFilter;
import com.sumal.security.provider.ApiKeyAuthenticationProvider;
import com.sumal.security.provider.JwtAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.stereotype.Component;

@Component
public class CustomSecurityConfigurer
        extends AbstractHttpConfigurer<CustomSecurityConfigurer, HttpSecurity> {

    private final SecurityAuthenticationFilter securityAuthenticationFilter;

    private final JwtTokenFilter jwtTokenFilter;

    private final ApiKeyFilter apiKeyFilter;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    private final ApiKeyAuthenticationProvider apiKeyAuthentication;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public CustomSecurityConfigurer(SecurityAuthenticationFilter securityAuthenticationFilter,
                                    JwtTokenFilter jwtTokenFilter, ApiKeyFilter apiKeyFilter,
                                    JwtAuthenticationProvider jwtAuthenticationProvider,
                                    ApiKeyAuthenticationProvider apiKeyAuthentication, ApplicationEventPublisher applicationEventPublisher) {
        this.securityAuthenticationFilter = securityAuthenticationFilter;
        this.jwtTokenFilter = jwtTokenFilter;
        this.apiKeyFilter = apiKeyFilter;
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
        this.apiKeyAuthentication = apiKeyAuthentication;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void init(HttpSecurity httpSecurity){
        System.out.println("CustomSecurityConfigurer init");
        httpSecurity.addFilterBefore(securityAuthenticationFilter, AuthorizationFilter.class)
                .addFilterBefore(jwtTokenFilter, SecurityAuthenticationFilter.class)
                .addFilterBefore(apiKeyFilter, JwtTokenFilter.class)
                .authenticationProvider(jwtAuthenticationProvider)
                .authenticationProvider(apiKeyAuthentication);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

        configureEventPublisher(authenticationManager);

        securityAuthenticationFilter.setAuthenticationManager(authenticationManager);

    }

    // Also, let's set the authentication event publisher so that we can receive
    // events on successful and failed authentication attempts
    // It's completely optional: if we do not set the publisher
    // it will default to NullEventPublisher implementation that does not publish events
    private void configureEventPublisher(AuthenticationManager authenticationManager) {


        if (authenticationManager instanceof ProviderManager) {
            ((ProviderManager) authenticationManager)
                    .setAuthenticationEventPublisher(
                            new DefaultAuthenticationEventPublisher(applicationEventPublisher));
        }
    }
}
