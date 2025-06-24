package es.miw.tfm.invierte.core.domain.model.enums;

public enum DepartmentType {
  SIMPLE,
  DUPLEX,
  TRIPLEX;

  public static DepartmentType of(String type) {
    return DepartmentType.valueOf(type);
  }
}
