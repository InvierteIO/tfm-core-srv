package es.miw.tfm.invierte.core.domain.exception;

/**
 * Custom exception for handling "Bad Request" scenarios.
 * This exception is thrown when a bad request error occurs, typically due to invalid input.
 * It extends the RuntimeException class.
 *
 * @author denilssonmn
 * @author devcastlecix
 */
public class BadRequestException extends RuntimeException {

  private static final String DESCRIPTION = "Bad Request Exception";

  public BadRequestException(String detail) {
    super(DESCRIPTION + ". " + detail);
  }

}
