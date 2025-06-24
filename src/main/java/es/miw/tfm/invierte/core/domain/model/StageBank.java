package es.miw.tfm.invierte.core.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.miw.tfm.invierte.core.domain.model.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StageBank {

  private Integer id;

  private String accountNumber;

  private String interbankAccountNumber;

  private Currency currency;

  private Bank bank;
}
