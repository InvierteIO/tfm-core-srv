package es.miw.tfm.invierte.core.infrastructure.data.entity;

import es.miw.tfm.invierte.core.domain.model.ProjectStage;
import es.miw.tfm.invierte.core.domain.model.enums.CommercializationCycle;
import es.miw.tfm.invierte.core.domain.model.enums.SubProjectStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * JPA entity representing a subproject in the system.
 * Maps the domain model to the database table `subproject`.
 * Stores information about the subproject's name, stage, address, status, and related project.
 *
 * @author denilssonmn
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subproject")
public class SubProjectEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 100, nullable = false)
  private String name;

  @Column(length = 4, nullable = false)
  private String stage;

  @Column(name = "kml_kmz_url", length = 1000)
  private String kmlKmzUrl;

  @Column(length = 8)
  private String zipCode;

  @Column(length = 200)
  private String address;

  @Column(name = "address_reference", length = 200)
  private String addressReference;

  @Column(name = "address_number", length = 10)
  private String addressNumber;

  @Column(length = 10, nullable = false)
  @Enumerated(EnumType.STRING)
  private SubProjectStatus status;

  @Column(name = "end_date")
  private LocalDate endDate;

  @Column(name = "hand_over_date")
  private LocalDate handOverDate;

  @Column(name = "commercialization_cycle", length = 12, nullable = false)
  @Enumerated(EnumType.STRING)
  private CommercializationCycle commercializationCycle;

  @ManyToOne
  @JoinColumn(name = "project_id", referencedColumnName = "id")
  private ProjectEntity project;

  @ManyToOne
  @JoinColumn(name = "location_code_id", referencedColumnName = "id")
  private LocationCodeEntity locationCode;

  @OneToMany(mappedBy = "subProject", cascade = CascadeType.ALL, orphanRemoval = true,
      fetch = FetchType.EAGER)
  List<SubProjectBankEntity> subProjectBanks = new ArrayList<>();

  @OneToMany(mappedBy = "subProject", cascade = CascadeType.ALL, orphanRemoval = true,
      fetch = FetchType.EAGER)
  List<SubProjectBonusTypeEntity> subProjectBonusTypes = new ArrayList<>();

  @OneToMany(mappedBy = "subProject", cascade = CascadeType.ALL, orphanRemoval = true)
  List<SubProjectDocumentEntity> subProjectDocumentEntities;

  @OneToMany(mappedBy = "subProject", cascade = CascadeType.ALL)
  List<SubProjectPropertyGroupEntity> subProjectPropertyGroups;

  @OneToMany(mappedBy = "subProject", cascade = CascadeType.ALL, orphanRemoval = true,
      fetch = FetchType.EAGER)
  List<SubProjectInfrastructureInstallationEntity> subProjectInfrastructureInstallationEntities
      = new ArrayList<>();

  @OneToMany(mappedBy = "subProject", cascade = CascadeType.ALL, orphanRemoval = true,
      fetch = FetchType.EAGER)
  List<SubProjectCatalogDetailEntity> subProjectCatalogDetailEntities = new ArrayList<>();

  /**
   * Constructs a SubProjectEntity from a domain ProjectStage object and its parent ProjectEntity.
   * Copies properties from the domain model and initializes related sub-entities
   * (banks and bonus types) from the corresponding domain lists.
   *
   * @param projectStage the domain ProjectStage to copy properties from
   * @param project the parent ProjectEntity to associate with this subproject
   */
  public SubProjectEntity(ProjectStage projectStage, ProjectEntity project) {
    BeanUtils.copyProperties(projectStage, this);
    this.project = project;

    Optional.ofNullable(projectStage.getStageBanks())
        .orElseGet(ArrayList::new)
        .forEach(stageBank -> this.subProjectBanks.add(
            new SubProjectBankEntity(stageBank, this)));

    Optional.ofNullable(projectStage.getStageBonusTypes())
        .orElseGet(ArrayList::new)
        .forEach(stageBonusType -> this.subProjectBonusTypes.add(
            new SubProjectBonusTypeEntity(stageBonusType, this)));

    Optional.ofNullable(projectStage.getStageInfraInstallations())
        .orElseGet(ArrayList::new)
        .forEach(stageInfraInstallation ->
            this.subProjectInfrastructureInstallationEntities.add(
            new SubProjectInfrastructureInstallationEntity(stageInfraInstallation, this)));

    Optional.ofNullable(projectStage.getStageCatalogDetails())
        .orElseGet(ArrayList::new)
        .forEach(stageCatalogDetail ->
            this.subProjectCatalogDetailEntities.add(
            new SubProjectCatalogDetailEntity(stageCatalogDetail, this)));

    Optional.ofNullable(projectStage.getLocationCode())
        .ifPresent(locationCodeModel ->
            this.locationCode = new LocationCodeEntity(locationCodeModel));
  }

  /**
   * Constructs a SubProjectEntity from a domain ProjectStage object.
   * Copies properties from the domain model and initializes related sub-entities
   * (banks and bonus types) from the corresponding domain lists.
   *
   * @param projectStage the domain ProjectStage to copy properties from
   */
  public SubProjectEntity(ProjectStage projectStage) {
    BeanUtils.copyProperties(projectStage, this);

    Optional.ofNullable(projectStage.getStageBanks())
        .orElseGet(ArrayList::new)
        .forEach(stageBank -> this.subProjectBanks.add(
            new SubProjectBankEntity(stageBank, this)));

    Optional.ofNullable(projectStage.getStageBonusTypes())
        .orElseGet(ArrayList::new)
        .forEach(stageBonusType -> this.subProjectBonusTypes.add(
            new SubProjectBonusTypeEntity(stageBonusType, this)));

  }

  /**
   * Converts this entity to a domain ProjectStage object.
   * Copies properties from the entity to the domain model and sets the related
   * stage banks and bonus types.
   *
   * @return the corresponding ProjectStage domain object
   */
  public ProjectStage toProjectStage() {
    ProjectStage projectStage = new ProjectStage();
    BeanUtils.copyProperties(this, projectStage);

    Optional.ofNullable(this.subProjectBanks)
        .orElseGet(ArrayList::new)
        .forEach(subProjectBankEntity ->
          projectStage.getStageBanks().add(subProjectBankEntity.toStageBank())
      );

    Optional.ofNullable(this.subProjectBonusTypes)
        .orElseGet(ArrayList::new)
        .forEach(subProjectBonusTypeEntity ->
          projectStage.getStageBonusTypes().add(subProjectBonusTypeEntity.toStageBonusTypes())
      );

    Optional.ofNullable(this.subProjectInfrastructureInstallationEntities)
        .orElseGet(ArrayList::new)
        .forEach(subProjectInfrastructureInstallationEntity ->
          projectStage.getStageInfraInstallations().add(
              subProjectInfrastructureInstallationEntity.toSubProjectInfrastructureInstallation())
      );

    Optional.ofNullable(this.subProjectCatalogDetailEntities)
        .orElseGet(ArrayList::new)
        .forEach(subProjectCatalogDetailEntity ->
          projectStage.getStageCatalogDetails().add(
              subProjectCatalogDetailEntity.toSubProjectCatalogDetail())
      );

    Optional.ofNullable(this.locationCode)
        .ifPresent(locationCodeEntity ->
            projectStage.setLocationCode(locationCodeEntity.toLocationCode()));

    return projectStage;
  }
}
