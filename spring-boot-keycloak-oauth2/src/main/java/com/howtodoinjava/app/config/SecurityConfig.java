package com.howtodoinjava.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  private KeycloakLogoutHandler keycloakLogoutHandler;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.authorizeHttpRequests(request -> {
      request.requestMatchers("/public").permitAll()
          .anyRequest().authenticated();
    });
    httpSecurity.oauth2Login(Customizer.withDefaults());
    httpSecurity.logout(logout -> {
      logout.logoutUrl("/logout");
      logout.addLogoutHandler(keycloakLogoutHandler);
    });
    return httpSecurity.build();
  }

  @Bean
  public ClientRegistrationRepository clientRepository() {
    return new InMemoryClientRegistrationRepository(keycloakClientRegistration());
  }

  private ClientRegistration keycloakClientRegistration() {
    return ClientRegistration.withRegistrationId("howtodoinjava-realm")
        .clientId("employee-management-api")
        .clientSecret("FsF1rAbtE8TuBjwH4fVaa79FXKluNA9a")
        .redirectUri("http://localhost:9090/login/oauth2/code/employee-management-api")
        .issuerUri("http://localhost:8080/realms/howtodoinjava")
        .authorizationUri("http://localhost:8080/realms/howtodoinjava/protocol/openid-connect/auth")
        .tokenUri("http://localhost:8080/realms/howtodoinjava/protocol/openid-connect/token")
        .userInfoUri("http://localhost:8080/realms/howtodoinjava/protocol/openid-connect/userinfo")
        .jwkSetUri("http://localhost:8080/realms/howtodoinjava/protocol/openid-connect/certs")
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .scope("openid")
        .userNameAttributeName("name")
        .build();
  }
}
