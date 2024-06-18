package vip.fairy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.DelegatingJwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  SessionAuthenticationStrategy sessionAuthenticationStrategy() {
    return new NullAuthenticatedSessionStrategy();
  }

  @Bean
  @Order(1)
  public SecurityFilterChain clientFilterChain(HttpSecurity http) throws Exception {
    http
        .securityMatcher("/")
        .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
            authorizationManagerRequestMatcherRegistry
                .anyRequest().permitAll()
        );

    return http.build();
  }

  /**
   * admin>editor>customer
   */
  @Bean
  @Order(2)
  public SecurityFilterChain resourceServerFilterChain(HttpSecurity http) throws Exception {
    DelegatingJwtGrantedAuthoritiesConverter authoritiesConverter =
        new DelegatingJwtGrantedAuthoritiesConverter(
            new JwtGrantedAuthoritiesConverter(),   // Default converter
            new KeycloakJwtRolesConverter());       // Custom Keycloak specific converter

    http
        .securityMatcher("/api/**")
        .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
            authorizationManagerRequestMatcherRegistry
                .requestMatchers(
                    new AntPathRequestMatcher("/api/customer")
                ).hasRole("CUSTOMER")

                .requestMatchers(
                    new AntPathRequestMatcher("/api/editor")
                ).hasRole("EDITOR")

                .requestMatchers(
                    new AntPathRequestMatcher("/api/admin")
                ).hasAnyRole("ADMIN")

                .anyRequest().authenticated()
        )
        .oauth2ResourceServer(oAuth2ResourceServerConfigurer ->
            oAuth2ResourceServerConfigurer
                .jwt(jwtConfigurer ->
                    jwtConfigurer.jwtAuthenticationConverter(
                        jwt -> new JwtAuthenticationToken(jwt, authoritiesConverter.convert(jwt)))
                )
                .accessDeniedHandler(new Oauth2AccessDeniedHandler())
        );

    return http.build();
  }
}
