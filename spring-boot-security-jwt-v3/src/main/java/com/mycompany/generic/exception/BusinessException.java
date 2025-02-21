package com.mycompany.generic.exception;

import com.mycompany.generic.dto.ErrorDTO;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BusinessException extends RuntimeException {

  private List<ErrorDTO> errors;

  public BusinessException(List<ErrorDTO> errors) {
    this.errors = errors;
  }

}
