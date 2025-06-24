package es.miw.tfm.invierte.core.domain.model;

import java.util.ArrayList;
import java.util.List;

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

  private Integer id;

  @NotBlank
  private String name;

  @NotBlank
  private String officeAddress;

  private String officeNumber;

  private String supervisor;

  private ProjectStatus status;

  @Positive
  private Integer stages;

  private String taxIdentificationNumber;

  private String description;

  private List<ProjectStage> projectStages = new ArrayList<>();

  public void setDefaultValues() {
    this.status = ProjectStatus.DRAFT;
  }
}
