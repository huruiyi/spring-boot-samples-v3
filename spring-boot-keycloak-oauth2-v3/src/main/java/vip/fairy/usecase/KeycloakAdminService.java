package vip.fairy.usecase;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import vip.fairy.model.LoginRequestDTO;
import vip.fairy.model.RegistrationRequestDTO;
import vip.fairy.model.ReturnDataDTO;

public interface KeycloakAdminService {

  Mono<ResponseEntity<ReturnDataDTO<String>>> createRegularUser(RegistrationRequestDTO dto);

  Mono<ResponseEntity<ReturnDataDTO<Map<String, String>>>> login(LoginRequestDTO dto);
}
