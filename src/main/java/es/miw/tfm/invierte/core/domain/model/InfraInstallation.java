package es.miw.tfm.invierte.core.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.miw.tfm.invierte.core.domain.model.enums.InstallationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InfraInstallation {

  private Integer id;

  private String name;

  private String code;

  private String dataType;

  private InstallationType installationType;

  private Catalog catalog;


}
