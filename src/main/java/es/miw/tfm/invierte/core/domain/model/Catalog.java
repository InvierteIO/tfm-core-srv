package es.miw.tfm.invierte.core.domain.model;

import java.util.List;

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
public class Catalog {

  private Integer id;

  private String code;

  private String name;

  private String description;

  private List<CatalogDetail> catalogDetails;

}
