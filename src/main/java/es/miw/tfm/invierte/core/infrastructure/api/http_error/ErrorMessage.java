package es.miw.tfm.invierte.core.infrastructure.api.http_error;

import lombok.Getter;
import lombok.ToString;

/**
 * Class representing an error message for HTTP responses.
 * This class encapsulates details about an error, including the type of exception,
 * the error message, and the associated HTTP status code.
 *
 * <p>It is used to provide a consistent structure for error responses in the API.
 *
 * @author denilssonmn
 */
@Getter
@ToString
public class ErrorMessage {

  private final String error;

  private final String message;

  private final Integer code;

  /**
   * Constructs an `ErrorMessage` object with the given exception and HTTP status code.
   *
   * @param exception the exception that caused the error
   * @param code the HTTP status code associated with the error
   */
  public ErrorMessage(Exception exception, Integer code) {
    this.error = exception.getClass().getSimpleName();
    this.message = exception.getMessage();
    this.code = code;
  }

}
