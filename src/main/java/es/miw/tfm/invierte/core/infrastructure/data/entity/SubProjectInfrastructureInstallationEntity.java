package es.miw.tfm.invierte.core.infrastructure.data.entity;

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

}
