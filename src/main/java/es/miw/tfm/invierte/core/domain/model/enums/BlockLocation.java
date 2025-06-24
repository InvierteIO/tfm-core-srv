package es.miw.tfm.invierte.core.domain.model.enums;

public enum BlockLocation {
  CORNER,
  CENTER;

  public static BlockLocation of(String blockLocation) {
    return BlockLocation.valueOf(blockLocation);
  }
}
