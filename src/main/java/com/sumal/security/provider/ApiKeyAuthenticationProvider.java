package com.sumal.security.provider;

import com.sumal.common.AuthUserType;
import com.sumal.common.Role;
import com.sumal.config.properties.ApiKeyClientsProperties;
import com.sumal.security.authentication.ApiKeyAuthentication;
import com.sumal.security.authentication.JwtAuthentication;
import com.sumal.security.user.AuthUser;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;
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
        if (!(authentication instanceof ApiKeyAuthentication apiKeyAuth)) {
            return null;
        }

        String apiKey = (String) apiKeyAuth.getCredentials();

        // Lookup clientId from configured API keys
        String clientId = apiKeysToClientIds.get(apiKey);

        if (clientId == null) {
            throw new BadCredentialsException("Invalid API Key");
        }

        AuthUser authUser = new AuthUser(
                clientId,
                List.of(Role.ROLE_ADMIN),
                AuthUserType.INTERNAL       // or EXTERNAL depending on logic
        );

        return ApiKeyAuthentication.authenticated(authUser);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ApiKeyAuthentication.class.isAssignableFrom(authentication);
    }
}
