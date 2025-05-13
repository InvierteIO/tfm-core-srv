package es.miw.tfm.invierte.core.domain.exception;

/**
 * Custom exception for handling "Not Found" scenarios.
 * This exception is thrown when a requested resource cannot be found.
 * It extends the RuntimeException class.
 *
 * @author denilssonmn
 * @author devcastlecix
 */
public class NotFoundException extends RuntimeException {

  private static final String DESCRIPTION = "Not Found Exception";

  public NotFoundException(String detail) {
    super(DESCRIPTION + ". " + detail);
  }

}
