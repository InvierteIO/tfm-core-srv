package es.miw.tfm.invierte.core.domain.exception;

public class NotFoundException extends RuntimeException {

  private static final String DESCRIPTION = "Not Found Exception";

  public NotFoundException(String detail) {
    super(DESCRIPTION + ". " + detail);
  }

}
