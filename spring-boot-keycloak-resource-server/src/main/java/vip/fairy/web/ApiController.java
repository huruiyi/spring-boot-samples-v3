package vip.fairy.web;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

  @GetMapping("/customer")
  public String customer() {
    return "Customer";
  }

  @GetMapping("/editor")
  public String editor() {
    return "Editor";
  }

  @GetMapping("/admin")
  public String admin() {
    return "Admin";
  }

  @GetMapping("/userInfo")
  public String anyAuthenticated(JwtAuthenticationToken principal) {
    String userId = "";
    String username = "";
    String firstName = "";

    Jwt user = principal.getToken();
    if (user.hasClaim("sub")) {
      userId = user.getClaim("sub");
    }

    if (user.hasClaim("preferred_username")) {
      username = user.getClaim("preferred_username");
    }

    if (user.hasClaim("given_name")) {
      firstName = user.getClaim("given_name");
    }

    return String.format("User ID: %s" + "\n" + "Username: %s" + "\n" + "First name: %s", userId, username, firstName);
  }
}
