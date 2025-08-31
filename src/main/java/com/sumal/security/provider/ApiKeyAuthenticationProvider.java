package com.sumal.security.provider;

import com.sumal.config.properties.ApiKeyClientsProperties;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ApiKeyAuthenticationProvider implements AuthenticationProvider {

    private final Map<String, String> apiKeysToClientIds;

    public ApiKeyAuthenticationProvider(ApiKeyClientsProperties apiKeyClientsProperties) {
        this.apiKeysToClientIds =
                apiKeyClientsProperties.getClients().entrySet().stream()
                        .collect(
                                Collectors.toMap(
                                        Map.Entry::getValue, Map.Entry::getKey, (oldValue, newValue) -> oldValue));
    }
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
