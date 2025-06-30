package es.miw.tfm.invierte.core.infrastructure.data.entity;

import es.miw.tfm.invierte.core.domain.model.CatalogDetail;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * JPA entity representing a catalog detail in the system. Maps the domain
 * model to the database table `catalog_detail`. Stores information about
 * the catalog detail's code, name, description, and related catalog.
 *
 * @author denilssonmn
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "catalog_detail")
public class CatalogDetailEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 10, nullable = false, unique = true)
  private String code;

  @Column(length = 150)
  private String name;

  @Column(length = 1000)
  private String description;

  @Column(length = 1000)
  private String other;

  @ManyToOne
  @JoinColumn(name = "catalog_id", referencedColumnName = "id")
  private CatalogEntity catalog;

  @OneToMany(mappedBy = "catalogDetail", cascade = CascadeType.ALL,
      orphanRemoval = true)
  List<SubProjectCatalogDetailEntity> subProjectCatalogDetailEntities;

  @OneToMany(mappedBy = "catalogDetail")
  List<ProjectDocumentEntity> projectDocuments;

  @OneToMany(mappedBy = "catalogDetail")
  List<PropertyGroupDocumentEntity> propertyGroupDocumentEntities;

  /**
   * Converts this entity to a domain CatalogDetail object. Copies properties
   * from the entity to the domain model.
   *
   * @return the corresponding CatalogDetail domain object
   * @author denilssonmn
   */
  public CatalogDetail toCatalogDetail() {
    CatalogDetail catalogDetail = new CatalogDetail();
    BeanUtils.copyProperties(this, catalogDetail);
    return catalogDetail;
  }
}
