package es.miw.tfm.invierte.core.domain.exception;

public class ForbiddenException extends RuntimeException {

  private static final String DESCRIPTION = "Forbidden Exception";

  public ForbiddenException(String detail) {
    super(DESCRIPTION + ". " + detail);
  }

}
