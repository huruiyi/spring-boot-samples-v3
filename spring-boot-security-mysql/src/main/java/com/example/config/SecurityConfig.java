package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * WebSecurityConfigurerAdapter
 * <p>
 * #org.springframework.security.web.FilterChainProxy
 * <p>
 * #org.springframework.security.web.session.SessionManagementFilter
 * <p>
 * #org.springframework.security.web.access.ExceptionTranslationFilter
 * <p>
 * #org.springframework.security.web.access.intercept.FilterSecurityInterceptor
 * <p>
 * org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests((auth) -> {
      auth.requestMatchers(HttpMethod.POST, "/register").permitAll();
      auth.requestMatchers("/**").hasAnyRole("USER");
      auth.anyRequest().authenticated();
    });

    http.logout(logoutConfigurer -> {
      logoutConfigurer.deleteCookies("remove");
      logoutConfigurer.invalidateHttpSession(false);
      logoutConfigurer.logoutUrl("/user/logout");
      logoutConfigurer.logoutSuccessUrl("/custom-logout");
    });

    http.formLogin(formLoginSpec -> {
      formLoginSpec.loginPage("/user/login").permitAll();
      formLoginSpec.loginProcessingUrl("/login");
      formLoginSpec.defaultSuccessUrl("/home/");
      // 使用 successForwardUrl 会导致/login 报错：type=Method Not Allowed, status=405 ->将此处的successForwardUrl使用defaultSuccessUrl替换
      formLoginSpec.usernameParameter("un");
      formLoginSpec.passwordParameter("pwd");
    });

    http.csrf(httpSecurityCsrfConfigurer -> {
      httpSecurityCsrfConfigurer.csrfTokenRepository(new CookieCsrfTokenRepository());
    });

    http.httpBasic(Customizer.withDefaults());
    return http.build();
  }

  @Bean
  public WebSecurityCustomizer ignoringCustomizer() {
    return (web) -> web.ignoring().requestMatchers("/css/**", "/image/**");
  }

}
