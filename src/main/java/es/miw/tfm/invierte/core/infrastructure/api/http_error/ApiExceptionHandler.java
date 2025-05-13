package es.miw.tfm.invierte.core.infrastructure.api.http_error;

import es.miw.tfm.invierte.core.domain.exception.BadGatewayException;
import es.miw.tfm.invierte.core.domain.exception.BadRequestException;
import es.miw.tfm.invierte.core.domain.exception.ConflictException;
import es.miw.tfm.invierte.core.domain.exception.ForbiddenException;
import es.miw.tfm.invierte.core.domain.exception.NotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.resource.NoResourceFoundException;
import org.springframework.web.server.ResponseStatusException;

/**
 * Global exception handler for the API.
 * This class provides centralized exception handling for various types of exceptions
 * that may occur during the execution of the application.
 *
 * <p>It maps specific exceptions to appropriate HTTP status codes and returns
 * a consistent error response structure using the `ErrorMessage` class.
 *
 * @see ErrorMessage
 *
 * @author denilssonmn
 */
@Log4j2
@ControllerAdvice
public class ApiExceptionHandler {

  /**
   * Handles exceptions when a requested resource is not found.
   * Returns a 404 Not Found status with a custom error message.
   *
   * @param exception the exception that was thrown
   * @return an `ErrorMessage` object containing error details
   */
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler({
      NoResourceFoundException.class,
      ResponseStatusException.class
      })
  @ResponseBody
  public ErrorMessage noResourceFoundRequest(Exception exception) {
    return new ErrorMessage(new NotFoundException(
        "Path not found. Try: **/actuator/info o **/swagger-ui.html o **/v3/api-docs"),
        HttpStatus.NOT_FOUND.value());
  }

  /**
   * Handles `NotFoundException` and returns a 404 Not Found status.
   *
   * @param exception the exception that was thrown
   * @return an `ErrorMessage` object containing error details
   */
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler({
      NotFoundException.class
      })
  @ResponseBody
  public ErrorMessage notFoundRequest(Exception exception) {
    return new ErrorMessage(exception, HttpStatus.NOT_FOUND.value());
  }

  /**
   * Handles bad request exceptions and returns a 400 Bad Request status.
   *
   * @param exception the exception that was thrown
   * @return an `ErrorMessage` object containing error details
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({
      BadRequestException.class,
      org.springframework.dao.DuplicateKeyException.class,
      org.springframework.web.bind.support.WebExchangeBindException.class,
      org.springframework.http.converter.HttpMessageNotReadableException.class
      })
  @ResponseBody
  public ErrorMessage badRequest(Exception exception) {
    return new ErrorMessage(exception, HttpStatus.BAD_REQUEST.value());
  }

  /**
   * Handles conflict exceptions and returns a 409 Conflict status.
   *
   * @param exception the exception that was thrown
   * @return an `ErrorMessage` object containing error details
   */
  @ResponseStatus(HttpStatus.CONFLICT)
  @ExceptionHandler({
      ConflictException.class
      })
  @ResponseBody
  public ErrorMessage conflict(Exception exception) {
    return new ErrorMessage(exception, HttpStatus.CONFLICT.value());
  }

  /**
   * Handles forbidden exceptions and returns a 403 Forbidden status.
   *
   * @param exception the exception that was thrown
   * @return an `ErrorMessage` object containing error details
   */
  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ExceptionHandler({
      ForbiddenException.class
      })
  @ResponseBody
  public ErrorMessage forbidden(Exception exception) {
    return new ErrorMessage(exception, HttpStatus.FORBIDDEN.value());
  }

  /**
   * Handles bad gateway exceptions and returns a 502 Bad Gateway status.
   *
   * @param exception the exception that was thrown
   * @return an `ErrorMessage` object containing error details
   */
  @ResponseStatus(HttpStatus.BAD_GATEWAY)
  @ExceptionHandler({
      BadGatewayException.class
      })
  @ResponseBody
  public ErrorMessage badGateway(Exception exception) {
    return new ErrorMessage(exception, HttpStatus.BAD_GATEWAY.value());
  }

  /**
   * Handles all other exceptions and returns a 500 Internal Server Error status.
   * Logs the exception details for debugging purposes.
   *
   * @param exception the exception that was thrown
   * @return an `ErrorMessage` object containing error details
   */
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler({
      Exception.class
      })
  @ResponseBody
  public ErrorMessage exception(
      Exception exception) {
    log.error("[MIW]::", exception);
    return new ErrorMessage(exception, HttpStatus.INTERNAL_SERVER_ERROR.value());
  }

}
