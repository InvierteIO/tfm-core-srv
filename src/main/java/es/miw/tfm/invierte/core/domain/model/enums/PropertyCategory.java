package es.miw.tfm.invierte.core.domain.model.enums;

public enum PropertyCategory {
  HOUSE,
  APARTMENT,
  LAND;

  public static PropertyCategory of(String category) {
    return PropertyCategory.valueOf(category);
  }
}
