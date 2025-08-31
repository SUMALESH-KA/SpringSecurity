package com.sumal.security.filter;

import com.sumal.common.AuthConstants;
import com.sumal.security.authentication.ApiKeyAuthentication;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class ApiKeyFilter extends AbstractAuthenticationCreationFilter{
    @Override
    protected Authentication buildAuthentication(HttpServletRequest request) {
        String apiKey = request.getHeader(AuthConstants.API_KEY_AUTHORIZATION_HEADER);

        if(apiKey == null || apiKey.isBlank()) {
            return null;
        }

        return ApiKeyAuthentication.unauthenticated(apiKey);
    }
}
