package es.miw.tfm.invierte.core.infrastructure.data.entity;

import es.miw.tfm.invierte.core.domain.model.enums.InstallationType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JPA entity representing an infrastructure installation in the system.
 * Maps the domain model to the database table `infrastructure_installation`.
 * Stores information about the installation's code, name, data type, description,
 * type, and its associations with subproject installations.
 *
 * @author denilssonmn
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "infrastructure_installation")
public class InfrastructureInstallationEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(unique = true, length = 4, nullable = false)
  private String code;

  @Column(length = 150, nullable = false)
  private String name;

  @Column(name = "data_type", length = 20, nullable = false)
  private String dataType;

  @Column(length = 1000)
  private String description;

  @Column(length = 150, nullable = false)
  @Enumerated(EnumType.STRING)
  private InstallationType installationType;

  @Column(length = 1000)
  private String other;

  @OneToMany(mappedBy = "infrastructureInstallation", cascade = CascadeType.ALL,
      orphanRemoval = true)
  List<SubProjectInfrastructureInstallationEntity> subProjectInfrastructureInstallationEntities;

}
