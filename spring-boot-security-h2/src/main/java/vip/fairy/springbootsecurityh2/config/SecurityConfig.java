package vip.fairy.springbootsecurityh2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // Other security configuration beans or methods

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.formLogin(Customizer.withDefaults());
    httpSecurity.httpBasic(Customizer.withDefaults());
    httpSecurity.csrf(AbstractHttpConfigurer::disable);
    httpSecurity.headers(headersConfigurer -> headersConfigurer.frameOptions(FrameOptionsConfig::disable));

    httpSecurity.authorizeHttpRequests(authorize -> {
      authorize.requestMatchers("/test").hasAnyAuthority("ADMIN");
      authorize.requestMatchers(new AntPathRequestMatcher("/roleNeeded")).hasAnyAuthority("ADMIN");
      authorize.requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll();
      authorize.anyRequest().authenticated();
    });
    return httpSecurity.build();
  }


}
