package es.miw.tfm.invierte.core.domain.model.enums;

public enum CommercializationCycle {

  ACTIVE,
  PRE_SALES,
  SALES,
  CLOSED;

  public static CommercializationCycle of(String cycle) {
    return CommercializationCycle.valueOf(cycle);
  }
}
