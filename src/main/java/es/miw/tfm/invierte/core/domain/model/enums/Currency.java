package es.miw.tfm.invierte.core.domain.model.enums;

public enum Currency {
  USD,
  PEN;

  public static Currency of(String currency) {
    return Currency.valueOf(currency);
  }
}
