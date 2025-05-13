package es.miw.tfm.invierte.core.domain.exception;

/**
 * Custom exception for handling "Conflict" scenarios.
 * This exception is thrown when a conflict occurs, such as a resource already existing.
 * It extends the RuntimeException class.
 *
 * @author denilssonmn
 * @author devcastlecix
 */
public class ConflictException extends RuntimeException {

  private static final String DESCRIPTION = "Conflict Exception";

  public ConflictException(String detail) {
    super(DESCRIPTION + ". " + detail);
  }

}
