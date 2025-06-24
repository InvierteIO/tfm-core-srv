package es.miw.tfm.invierte.core.domain.model.enums;

public enum InstallationType {
  DEFINED,
  PROJECTED;

  public static InstallationType of(String installationType) {
    return InstallationType.valueOf(installationType);
  }
}
