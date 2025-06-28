package es.miw.tfm.invierte.core.domain.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.miw.tfm.invierte.core.domain.model.enums.BlockLocation;
import es.miw.tfm.invierte.core.domain.model.enums.Currency;
import es.miw.tfm.invierte.core.domain.model.enums.DepartmentType;
import es.miw.tfm.invierte.core.domain.model.enums.Flag;
import es.miw.tfm.invierte.core.domain.model.enums.PropertyCategory;
import es.miw.tfm.invierte.core.domain.model.enums.RoadWay;
import es.miw.tfm.invierte.core.domain.model.enums.TowerLocation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PropertyGroup {

  @EqualsAndHashCode.Include
  private Integer id;

  private String name;

  private PropertyCategory propertyCategory;

  private Double perimeter;

  private Double area;

  private Flag parkingSpace;

  private Flag frontPark;

  private Double price;

  private Currency currency;

  private Double annualInterestRate;

  private Integer totalBathrooms;

  private TowerLocation towerLocation;

  private DepartmentType type;

  private Integer totalRooms;

  private Double areaFloorTwo;

  private Double areaFloorThree;

  private Integer roofedArea;

  private Integer totalFloors;

  private BlockLocation blockLocation;

  private RoadWay roadWay;

  private List<PropertyFeature> propertyFeatures = new ArrayList<>();

  private List<HouseFloorArea> houseFloorAreas = new ArrayList<>();

}
