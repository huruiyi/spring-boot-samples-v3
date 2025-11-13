package com.example.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;

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

  @Autowired
  private DataSource dataSource;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }


  @Bean
  public CustomLogoutHandler customLogoutHandler() {
    return new CustomLogoutHandler();
  }

  @Bean
  public LogoutSuccessHandler customLogoutSuccessHandler() {
    return new CustomLogoutSuccessHandler();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests((auth) -> {
      auth.requestMatchers("/user/login").permitAll();
      auth.requestMatchers(HttpMethod.POST, "/register").permitAll();
      auth.requestMatchers("/**").hasAnyRole("USER");
      auth.anyRequest().authenticated();
    });

    http.logout(logoutConfigurer -> {
      logoutConfigurer.deleteCookies("SESSION");
      logoutConfigurer.invalidateHttpSession(true);
      logoutConfigurer.logoutUrl("/user/logout");
      logoutConfigurer.logoutSuccessUrl("/user/login");
      logoutConfigurer.addLogoutHandler(customLogoutHandler());
      logoutConfigurer.logoutSuccessHandler(customLogoutSuccessHandler());
    });

    http.formLogin(formLoginSpec -> {
      formLoginSpec.loginPage("/user/login");
      formLoginSpec.loginProcessingUrl("/login");
      formLoginSpec.defaultSuccessUrl("/home/");
      // 使用 successForwardUrl 会导致/login 报错：type=Method Not Allowed, status=405 ->将此处的successForwardUrl使用defaultSuccessUrl替换
      formLoginSpec.usernameParameter("un");
      formLoginSpec.passwordParameter("pwd");
    });

    http.sessionManagement(s -> {
      s.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
      s.maximumSessions(1);
    });

    http.csrf(httpSecurityCsrfConfigurer -> {
      httpSecurityCsrfConfigurer.csrfTokenRepository(new CookieCsrfTokenRepository());
    });

    http.rememberMe(r -> {
      r.tokenRepository(persistentTokenRepository());
      r.rememberMeParameter("rm");
      r.tokenValiditySeconds(86400);//24小时有效
      r.useSecureCookie(true);
    });
    http.httpBasic(AbstractHttpConfigurer::disable);
    return http.build();
  }


  @Bean
  public PersistentTokenRepository persistentTokenRepository() {
    JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
    tokenRepository.setDataSource(dataSource);
    // 如果表不存在，自动创建（仅用于开发环境）
    // tokenRepository.setCreateTableOnStartup(true);
    return tokenRepository;
  }


  @Bean
  public WebSecurityCustomizer ignoringCustomizer() {
    return (web) -> web.ignoring().requestMatchers("/css/**", "/image/**");
  }

  @Bean
  public HttpSessionEventPublisher httpSessionEventPublisher() {
    return new HttpSessionEventPublisher();
  }


}
