package com.sumal.security.filter;


import com.sumal.common.AuthConstants;
import com.sumal.security.authentication.UserAuthentication;
import com.sumal.security.exception.TokenAuthenticationException;
import com.sumal.security.user.AuthUser;
import com.sumal.security.user.AuthUserCache;
import com.sumal.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class SecurityAuthenticationFilter extends OncePerRequestFilter {

  private final AuthUserCache authUserCache;
  private final JwtService jwtService;

  public SecurityAuthenticationFilter(AuthUserCache authUserCache, JwtService jwtService) {
    this.authUserCache = authUserCache;
    this.jwtService = jwtService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String authenticationHeader = request.getHeader(AuthConstants.AUTHORIZATION_HEADER);

    if (authenticationHeader == null) {
      // Authentication token is not present, let's rely on anonymous authentication
      filterChain.doFilter(request, response);
      return;
    }
    if(!authenticationHeader.startsWith("Bearer ")) {
      throw new TokenAuthenticationException("bearer token is missing from the request header");
    }
    String jwtToken = authenticationHeader.substring(7);

    if(jwtToken.isBlank()) {
      throw new TokenAuthenticationException("jwt token is blank");
    }

    AuthUser authUser = jwtService.resolveJwtToken(jwtToken);

//
//    AuthUser authUser =
//        authUserCache
//            .getByToken(authenticationHeader)
//            .orElseThrow(() -> new TokenAuthenticationException("Token is not valid"));

    UserAuthentication authentication = new UserAuthentication(authUser);

    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
    securityContext.setAuthentication(authentication);
    SecurityContextHolder.setContext(securityContext);

    filterChain.doFilter(request, response);
  }
}
