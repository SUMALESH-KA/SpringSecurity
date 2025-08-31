package com.sumal.security.filter;

import com.sumal.common.AuthConstants;
import com.sumal.security.authentication.JwtAuthentication;
import com.sumal.security.exception.TokenAuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenFilter extends AbstractAuthenticationCreationFilter{
    @Override
    protected Authentication buildAuthentication(HttpServletRequest request) {
        String jwtToken = request.getHeader(AuthConstants.JWT_AUTHORIZATION_HEADER);

        if (jwtToken == null) {
            return null;
        }
        String FinalToken = StripBearer(jwtToken);

        if(FinalToken.isBlank()) {
            return null;
        }
        return JwtAuthentication.unauthenticated(FinalToken);

    }

    private String StripBearer(String jwtToken) {
        if (!jwtToken.startsWith("Bearer ")) {
            throw new TokenAuthenticationException("Bearer token is missing from the request header");
        }
        return jwtToken.substring(7);
    }

}
