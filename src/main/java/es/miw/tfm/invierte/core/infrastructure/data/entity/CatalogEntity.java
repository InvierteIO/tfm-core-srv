package es.miw.tfm.invierte.core.infrastructure.data.entity;

import es.miw.tfm.invierte.core.domain.model.Catalog;
import es.miw.tfm.invierte.core.domain.model.CatalogDetail;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
 * JPA entity representing a catalog in the system. Maps the domain model
 * to the database table `catalog`. Stores information about the catalog's
 * code, name, description, and its related catalog details.
 *
 * @author denilssonmn
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "catalog")
public class CatalogEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 4, nullable = false, unique = true)
  private String code;

  @Column(length = 150, nullable = false, unique = true)
  private String name;

  @Column(length = 1000)
  private String description;

  @Column(length = 1000)
  private String other;

  @OneToMany(mappedBy = "catalog", cascade = CascadeType.ALL, orphanRemoval = true,
      fetch = FetchType.EAGER)
  private List<CatalogDetailEntity> catalogDetails = new ArrayList<>();

  /**
   * Converts this entity to a domain Catalog object. Copies properties from
   * the entity to the domain model and sets the related catalog details in
   * the domain object.
   *
   * @return the corresponding Catalog domain object
   * @author denilssonmn
   */
  public Catalog toCatalog() {
    Catalog catalog = new Catalog();
    BeanUtils.copyProperties(this, catalog);

    Optional.ofNullable(this.catalogDetails)
        .orElseGet(ArrayList::new)
        .forEach(catalogDetailEntity -> {
          CatalogDetail catalogDetail = catalogDetailEntity.toCatalogDetail();
          catalog.getCatalogDetails().add(catalogDetail);
        });

    return catalog;
  }
}
