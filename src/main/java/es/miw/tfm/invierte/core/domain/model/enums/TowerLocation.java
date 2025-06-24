package es.miw.tfm.invierte.core.domain.model.enums;

public enum TowerLocation {
  INTERIOR,
  EXTERIOR;

  public static TowerLocation of(String location) {
    return TowerLocation.valueOf(location);
  }
}
