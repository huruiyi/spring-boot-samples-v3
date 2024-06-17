package vip.fairy.springbootsecurityh2.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableMethodSecurity
@RestController
public class WebController {

  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping("/test")
  public Authentication getAuthenticatedUser() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

  @GetMapping("/")
  public String publicResource() {
    return "This resource only requires authentication";
  }

  @GetMapping("/roleAdminNeeded")
  @PreAuthorize("hasAuthority('ADMIN')")
  public String privateAdminResource() {
    return "This resource requires the ADMIN role";
  }


  @GetMapping("/roleUserNeeded")
  @PreAuthorize("hasAuthority('USER')")
  public String privateUserResource() {
    return "This resource requires the USER role";
  }
}

