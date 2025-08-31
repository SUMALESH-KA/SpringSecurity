package com.sumal.config;


import com.sumal.security.filter.ApiKeyFilter;
import com.sumal.security.filter.JwtTokenFilter;
import com.sumal.security.filter.SecurityAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@EnableMethodSecurity // allow to specify access via annotations
@Configuration
public class SecurityConfig {

  private final SecurityAuthenticationFilter securityAuthenticationFilter;

  private final AuthenticationEntryPoint authenticationEntryPoint;

  private final AccessDeniedHandler accessDeniedHandler;

  private final JwtTokenFilter JwtTokenFilter;

  private final ApiKeyFilter ApiKeyFilter;

  public SecurityConfig(
      SecurityAuthenticationFilter securityAuthenticationFilter,
      AuthenticationEntryPoint authenticationEntryPoint,
      AccessDeniedHandler accessDeniedHandler,
      JwtTokenFilter JwtTokenFilter,ApiKeyFilter ApiKeyFilter) {

    this.securityAuthenticationFilter = securityAuthenticationFilter;
    this.authenticationEntryPoint = authenticationEntryPoint;
    this.accessDeniedHandler = accessDeniedHandler;
    this.JwtTokenFilter = JwtTokenFilter;
    this.ApiKeyFilter = ApiKeyFilter;
  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(
                            "/swagger-ui.html", "/swagger-ui/**",
                            "/v3/api-docs", "/v3/api-docs/swagger-config",
                            "/api/auth/login", "/api/users"
                    ).permitAll()
                    .anyRequest().authenticated()
            )
            .exceptionHandling(eh -> eh
                    .accessDeniedHandler(accessDeniedHandler)
                    .authenticationEntryPoint(authenticationEntryPoint)
            );
    http.addFilterBefore(securityAuthenticationFilter, AuthorizationFilter.class);
    http.addFilterBefore(JwtTokenFilter, SecurityAuthenticationFilter.class);
    http.addFilterBefore(ApiKeyFilter, JwtTokenFilter.class);

    return http.build();
  }

}
