package es.miw.tfm.invierte.core.domain.exception;

public class BadGatewayException extends RuntimeException {

  private static final String DESCRIPTION = "Bad Gateway Exception";

  public BadGatewayException(String detail) {
    super(DESCRIPTION + ". " + detail);
  }

}
