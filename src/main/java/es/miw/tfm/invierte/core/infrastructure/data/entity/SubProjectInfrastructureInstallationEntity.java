package es.miw.tfm.invierte.core.infrastructure.data.entity;

import es.miw.tfm.invierte.core.domain.model.StageInfraInstallation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * JPA entity representing the association between a subproject and an infrastructure installation.
 * Maps the domain model to the database table `subproject_infrastructure_installation`.
 * Stores information about the installation field value and its relation to subprojects and
 * infrastructure installations.
 *
 * @author denilssonmn
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subproject_infrastructure_installation")
public class SubProjectInfrastructureInstallationEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 500, nullable = false, name = "field_value")
  private String fieldValue;


  @ManyToOne
  @JoinColumn(name = "subproject_id", referencedColumnName = "id")
  private SubProjectEntity subProject;

  @ManyToOne
  @JoinColumn(name = "infrastructure_installation_id", referencedColumnName = "id")
  private InfrastructureInstallationEntity infrastructureInstallation;

  /**
   * Constructs a SubProjectInfrastructureInstallationEntity from a domain
   * StageInfraInstallation object and its parent SubProjectEntity. Copies
   * properties from the domain model and initializes the related infrastructure
   * installation entity from the domain object.
   *
   * @param stageInfraInstallation the domain StageInfraInstallation to copy from
   * @param subProjectEntity the parent SubProjectEntity to associate with
   * @author denilssonmn
   */
  public SubProjectInfrastructureInstallationEntity(StageInfraInstallation stageInfraInstallation,
      SubProjectEntity subProjectEntity) {
    BeanUtils.copyProperties(stageInfraInstallation, this);
    this.subProject = subProjectEntity;

    InfrastructureInstallationEntity infrastructureInstallationEntity =
        new InfrastructureInstallationEntity();
    BeanUtils.copyProperties(stageInfraInstallation.getInfraInstallation(),
        infrastructureInstallationEntity);
    this.infrastructureInstallation = infrastructureInstallationEntity;
  }

  /**
   * Converts this entity to a domain StageInfraInstallation object. Copies
   * properties from the entity and sets the related infrastructure installation
   * in the domain model.
   *
   * @return the corresponding StageInfraInstallation domain object
   * @author denilssonmn
   */
  public StageInfraInstallation toSubProjectInfrastructureInstallation() {
    StageInfraInstallation stageInfraInstallation = new StageInfraInstallation();
    BeanUtils.copyProperties(this, stageInfraInstallation);
    stageInfraInstallation.setInfraInstallation(
        this.infrastructureInstallation.toInfrastructureInstallation());
    return stageInfraInstallation;
  }
}
