package es.miw.tfm.invierte.core.infrastructure.data.entity;

import es.miw.tfm.invierte.core.domain.model.Catalog;
import es.miw.tfm.invierte.core.domain.model.InfraInstallation;
import es.miw.tfm.invierte.core.domain.model.enums.InstallationType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

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

  @OneToMany(mappedBy = "infrastructureInstallation", cascade = CascadeType.ALL,
      orphanRemoval = true)
  List<SubProjectCatalogDetailEntity> subProjectCatalogDetailEntities;


  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "catalog_id", referencedColumnName = "id")
  private CatalogEntity catalog;

  /**
   * Converts this entity to a domain InfraInstallation object. Copies
   * properties from the entity to the domain model and sets the related
   * catalog in the domain object.
   *
   * @return the corresponding InfraInstallation domain object
   * @author denilssonmn
   */
  public InfraInstallation toInfrastructureInstallation() {
    InfraInstallation infraInstallation = new InfraInstallation();
    BeanUtils.copyProperties(this, infraInstallation);

    Catalog catalogDto = Optional.ofNullable(this.catalog)
        .map(CatalogEntity::toCatalog)
        .orElse(null);
    infraInstallation.setCatalog(catalogDto);

    return infraInstallation;
  }
}
