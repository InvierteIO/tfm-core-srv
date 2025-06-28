package es.miw.tfm.invierte.core.domain.model.enums;

public enum Flag {
  YES,
  NO;

  public static Flag of(String value) {
    return Flag.valueOf(value);
  }
}
