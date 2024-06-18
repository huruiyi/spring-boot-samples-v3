package vip.fairy.resourceserver.web;

import java.security.Principal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.fairy.resourceserver.model.UserProfile;

@Slf4j
@RestController
public class RestResource {

  @GetMapping("/api/users/me")
  public ResponseEntity<Principal> profile(Principal principal) {
    Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    UserProfile profile = new UserProfile();
    profile.setName(jwt.getSubject());
    log.info("profile:{}", profile);

    return ResponseEntity.ok(principal);
  }
}
