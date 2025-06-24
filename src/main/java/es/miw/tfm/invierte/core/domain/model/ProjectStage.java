package es.miw.tfm.invierte.core.domain.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.miw.tfm.invierte.core.domain.model.enums.CommercializationCycle;
import es.miw.tfm.invierte.core.domain.model.enums.SubProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectStage {

  private Integer id;

  private String name;

  private String stage;

  private CommercializationCycle commercializationCycle;

  private SubProjectStatus status;

  private List<StageBank> stageBanks = new ArrayList<>();

  private List<StageBonusType> stageBonusTypes = new ArrayList<>();

  private List<StageInfraInstallation> stageInfraInstallations;

  private List<StageCatalogDetail> stageCatalogDetails;

  private LocalDate endDate;

  private LocalDate handOverDate;

}
