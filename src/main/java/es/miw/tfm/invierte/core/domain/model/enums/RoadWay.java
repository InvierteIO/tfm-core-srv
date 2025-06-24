package es.miw.tfm.invierte.core.domain.model.enums;

public enum RoadWay {
  AVENUE,
  STREET;

  public static RoadWay of(String roadWay) {
    return RoadWay.valueOf(roadWay);
  }
}
