package es.miw.tfm.invierte.core.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.miw.tfm.invierte.core.domain.model.enums.ProjectStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Project {

  private int id;

  @NotBlank
  private String name;

  @NotBlank
  private String officeAddress;

  private String officeNumber;

  @NotBlank
  private String supervisor;

  private ProjectStatus status;

  @Positive
  private Integer stages;

  private String taxIdentificationNumber;

  private String description;

  public void setDefaultValues() {
    this.status = ProjectStatus.DRAFT;
  }
}
