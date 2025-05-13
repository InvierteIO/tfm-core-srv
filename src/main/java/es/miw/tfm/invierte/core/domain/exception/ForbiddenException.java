package es.miw.tfm.invierte.core.domain.exception;

/**
 * Custom exception for handling "Forbidden" scenarios.
 * This exception is thrown when an operation is not allowed due to insufficient permissions.
 * It extends the RuntimeException class.
 *
 * @author denilssonmn
 * @author devcastlecix
 */
public class ForbiddenException extends RuntimeException {

  private static final String DESCRIPTION = "Forbidden Exception";

  public ForbiddenException(String detail) {
    super(DESCRIPTION + ". " + detail);
  }

}
