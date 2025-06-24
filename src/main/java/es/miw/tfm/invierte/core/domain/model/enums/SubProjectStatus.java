package es.miw.tfm.invierte.core.domain.model.enums;

public enum SubProjectStatus {
  ACTIVE,
  PRE_SALE,
  DRAFT,
  SALE,
  CLOSED;

  public static SubProjectStatus of(String status) {
    return SubProjectStatus.valueOf(status);
  }
}
