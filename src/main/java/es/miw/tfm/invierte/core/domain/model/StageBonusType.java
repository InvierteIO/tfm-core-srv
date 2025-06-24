package es.miw.tfm.invierte.core.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StageBonusType {

  private Integer id;

  private String typeValue;

  private FinancialBonusType financialBonusType;

}
