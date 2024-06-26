package vip.fairy.controller;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import vip.fairy.model.LoginRequestDTO;
import vip.fairy.model.RegistrationRequestDTO;
import vip.fairy.model.ReturnDataDTO;
import vip.fairy.usecase.KeycloakAdminService;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

  private final KeycloakAdminService keycloakAdminService;

  @PostMapping("/registration")
  public Mono<ResponseEntity<ReturnDataDTO<String>>> registerUser(@RequestBody RegistrationRequestDTO registrationRequestDTO) {
    return keycloakAdminService.createRegularUser(registrationRequestDTO);
  }

  @PostMapping("/login")
  public Mono<ResponseEntity<ReturnDataDTO<Map<String, String>>>> login(@RequestBody LoginRequestDTO loginRequestDTO) {
    return keycloakAdminService.login(loginRequestDTO);
  }

}
