package es.miw.tfm.invierte.core.infrastructure.api.http_error;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ErrorMessage {

  private final String error;

  private final String message;

  private final Integer code;

  public ErrorMessage(Exception exception, Integer code) {
    this.error = exception.getClass().getSimpleName();
    this.message = exception.getMessage();
    this.code = code;
  }

}
