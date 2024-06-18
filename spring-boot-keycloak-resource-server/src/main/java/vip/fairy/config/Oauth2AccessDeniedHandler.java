package vip.fairy.config;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class Oauth2AccessDeniedHandler implements AccessDeniedHandler {

  @Builder
  @JsonSerialize
  static class ErrorBody {

    private HttpStatus status;
    private String errMsg;
    private String path;


    @SneakyThrows
    public String json() {
      return String.format("{" +
              "\"status\": %s,\n" +
              "\"message\": %s,\n" +
              "\"path\": %s,\n" +
              "}",
          status.toString(), errMsg, path
      );
    }
  }

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
      throws IOException {
    String errorMessage = accessDeniedException.getLocalizedMessage();

    String message = ErrorBody.builder()
        .status(HttpStatus.FORBIDDEN)
        .errMsg(errorMessage)
        .path(request.getRequestURI())
        .build()
        .json();

    response.setStatus(HttpStatus.FORBIDDEN.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.getWriter().write(message);
  }
}
