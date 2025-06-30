package es.miw.tfm.invierte.core.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import es.miw.tfm.invierte.core.domain.model.enums.CommercializationCycle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Property {

  private Integer id;

  private String codeSystem;

  private String codeEnterprise;

  private String name;

  @JsonProperty("isAvailableSale")
  private boolean isAvailableSale;

  private Double price;

  private CommercializationCycle commercializationCycle;

  @JsonProperty("isParkingSpace")
  private boolean isParkingSpace;

  private String address;

}
