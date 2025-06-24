package es.miw.tfm.invierte.core.domain.model.enums;

public enum FinancialEntityType {
  BANK,
  RURAL_BANK;

  public static FinancialEntityType of(String type) {
    return FinancialEntityType.valueOf(type);
  }

}
