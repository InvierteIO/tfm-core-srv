package es.miw.tfm.invierte.core.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.miw.tfm.invierte.core.domain.model.enums.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectSummaryDto {

  private Integer id;

  private String name;

  private String address;

  private Integer numberApartments = 0;

  private Integer numberLands = 0;

  private Integer numberHouses = 0;

  private Integer stages;

  private Double areaTotal = 0.0;

  private ProjectStatus status;

  public void acumularTotales(Integer numberApartments, Integer numberLands, Integer numberHouses, Double areaTotal) {
    this.numberApartments += numberApartments;
    this.numberLands += numberLands;
    this.numberHouses += numberHouses;
    this.areaTotal += areaTotal;
  }


}
