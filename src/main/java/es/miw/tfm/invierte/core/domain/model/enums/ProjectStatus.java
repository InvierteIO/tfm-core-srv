package es.miw.tfm.invierte.core.domain.model.enums;

public enum ProjectStatus {
  ACTIVE,
  DISABLED,
  DRAFT,
  NOPUBLISHED,
  PUBLISHED;

  public static ProjectStatus of(String status) {
    return ProjectStatus.valueOf(status);
  }

}
