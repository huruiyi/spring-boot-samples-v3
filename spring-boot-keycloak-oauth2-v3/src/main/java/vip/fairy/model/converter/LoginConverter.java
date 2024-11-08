package vip.fairy.model.converter;

import reactor.core.publisher.Mono;
import vip.fairy.business.Login;
import vip.fairy.model.LoginRequestDTO;

public final class LoginConverter {

  public static Mono<Login> fromLoginRequestDTO(final LoginRequestDTO dto) {
    return Mono.just(
        Login.builder()
            .username(dto.getUsername())
            .email(dto.getEmail())
            .password(dto.getPassword())
            .build());
  }
}
