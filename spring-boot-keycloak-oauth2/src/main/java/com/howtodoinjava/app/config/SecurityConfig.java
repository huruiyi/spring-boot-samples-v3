package com.howtodoinjava.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  private KeycloakLogoutHandler keycloakLogoutHandler;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.authorizeHttpRequests()
//                .requestMatchers("/public").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .oauth2Login();

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
}
