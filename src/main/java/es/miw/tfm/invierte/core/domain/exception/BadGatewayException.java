package es.miw.tfm.invierte.core.domain.exception;

/**
 * Custom exception for handling "Bad Gateway" scenarios.
 * This exception is thrown when a bad gateway error occurs.
 * It extends the RuntimeException class.
 *
 * @author denilssonmn
 * @author devcastlecix
 */
public class BadGatewayException extends RuntimeException {

  private static final String DESCRIPTION = "Bad Gateway Exception";

  public BadGatewayException(String detail) {
    super(DESCRIPTION + ". " + detail);
  }

}
