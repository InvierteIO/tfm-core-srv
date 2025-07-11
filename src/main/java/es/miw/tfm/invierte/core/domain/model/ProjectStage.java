package es.miw.tfm.invierte.core.domain.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
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

  private String address;

  private String addressNumber;

  private String addressReference;

  private String zipCode;

  private String kmlKmzUrl;

  private LocationCode locationCode;

  private List<StageBank> stageBanks = new ArrayList<>();

  private List<StageBonusType> stageBonusTypes = new ArrayList<>();

  private List<StageInfraInstallation> stageInfraInstallations = new ArrayList<>();

  private List<StageCatalogDetail> stageCatalogDetails = new ArrayList<>();

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate endDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate handOverDate;

}
