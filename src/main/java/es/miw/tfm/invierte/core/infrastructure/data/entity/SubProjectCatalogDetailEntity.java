package es.miw.tfm.invierte.core.infrastructure.data.entity;

import es.miw.tfm.invierte.core.domain.model.StageCatalogDetail;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * JPA entity representing the association between a subproject and a catalog
 * detail, including its situation and related infrastructure installation.
 * Maps the domain model to the database table `subproject_catalog_detail`.
 *
 * @author denilssonmn
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subproject_catalog_detail")
public class SubProjectCatalogDetailEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 10)
  private String situation;

  @ManyToOne
  @JoinColumn(name = "subproject_id", referencedColumnName = "id")
  private SubProjectEntity subProject;

  @ManyToOne
  @JoinColumn(name = "infrastructure_installation_id", referencedColumnName = "id")
  private InfrastructureInstallationEntity infrastructureInstallation;

  @ManyToOne
  @JoinColumn(name = "catalog_detail_id", referencedColumnName = "id")
  private CatalogDetailEntity catalogDetail;

  /**
   * Constructs a SubProjectCatalogDetailEntity from a domain StageCatalogDetail
   * object and its parent SubProjectEntity. Copies properties from the domain
   * model and initializes the related infrastructure installation and catalog
   * detail entities from the domain object.
   *
   * @param stageCatalogDetail the domain StageCatalogDetail to copy from
   * @param subProjectEntity the parent SubProjectEntity to associate with
   * @author denilssonmn
   */
  public SubProjectCatalogDetailEntity(StageCatalogDetail stageCatalogDetail,
      SubProjectEntity subProjectEntity) {
    BeanUtils.copyProperties(stageCatalogDetail, this);
    this.subProject = subProjectEntity;

    Optional.ofNullable(stageCatalogDetail.getInfraInstallation())
        .ifPresent(infraInstallation -> {
          InfrastructureInstallationEntity infrastructureInstallationEntity =
              new InfrastructureInstallationEntity();
          BeanUtils.copyProperties(infraInstallation,
              infrastructureInstallationEntity);
          this.infrastructureInstallation = infrastructureInstallationEntity;
        });

    Optional.ofNullable(stageCatalogDetail.getCatalogDetail())
        .ifPresent(catalogDetailDto -> {
          CatalogDetailEntity catalogDetailEntity = new CatalogDetailEntity();
          BeanUtils.copyProperties(catalogDetailDto, catalogDetailEntity);
          this.catalogDetail = catalogDetailEntity;
        });
  }

  /**
   * Converts this entity to a domain StageCatalogDetail object. Copies
   * properties from the entity to the domain model and sets the related
   * catalog detail in the domain object.
   *
   * @return the corresponding StageCatalogDetail domain object
   * @author denilssonmn
   */
  public StageCatalogDetail toSubProjectCatalogDetail() {
    StageCatalogDetail stageCatalogDetail = new StageCatalogDetail();
    BeanUtils.copyProperties(this, stageCatalogDetail);

    Optional.ofNullable(this.infrastructureInstallation)
        .ifPresent(infraInstallation ->
          stageCatalogDetail.setInfraInstallation(
              infraInstallation.toInfrastructureInstallation()));

    Optional.ofNullable(this.catalogDetail)
        .ifPresent(catalogDetailDto ->
          stageCatalogDetail.setCatalogDetail(catalogDetailDto.toCatalogDetail()));


    return stageCatalogDetail;
  }
}
