package es.miw.tfm.invierte.core.infrastructure.data.entity;

import es.miw.tfm.invierte.core.domain.model.PropertyGroupDocument;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * JPA entity representing a document associated with a property group.
 * Maps to the `property_group_document` table and stores metadata such as
 * filename, path, creation date, and description. Maintains relationships
 * with {@link PropertyGroupEntity} and {@link CatalogDetailEntity}.
 * Provides conversion methods to and from the domain model {@link PropertyGroupDocument}.
 *
 * @author denilssonmn
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "property_group_document")
public class PropertyGroupDocumentEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 100, nullable = false)
  private String filename;

  @Column(length = 200, nullable = false)
  private String path;

  @Column(name = "creation_date", nullable = false)
  private LocalDateTime creationDate;

  @Column(length = 200, nullable = false)
  private String description;

  @ManyToOne
  @JoinColumn(name = "subproject_property_group_id", referencedColumnName = "id")
  private SubProjectPropertyGroupEntity subProjectPropertyGroup;

  @ManyToOne
  @JoinColumn(name = "catalog_detail_id", referencedColumnName = "id")
  private CatalogDetailEntity catalogDetail;


  /**
   * JPA entity representing a document associated with a property group.
   * Maps to the `property_group_document` table and stores metadata such as
   * filename, path, creation date, and description. Maintains relationships
   * with {@link SubProjectPropertyGroupEntity} and {@link CatalogDetailEntity}.
   * Provides conversion methods to and from the domain model {@link PropertyGroupDocument}.
   *
   * @author denilssonmn
   */
  public PropertyGroupDocumentEntity(PropertyGroupDocument propertyGroupDocumentDto,
      SubProjectPropertyGroupEntity subProjectPropertyGroupEntity,
      CatalogDetailEntity catalogDetailEntity) {
    BeanUtils.copyProperties(propertyGroupDocumentDto, this);
    this.creationDate = LocalDateTime.now();
    this.subProjectPropertyGroup = subProjectPropertyGroupEntity;
    this.catalogDetail = catalogDetailEntity;
  }

  /**
   * Converts this entity to its corresponding domain model {@link PropertyGroupDocument}.
   * Copies properties, sets the name and creation date, and maps the associated catalog detail.
   *
   * @return the domain model representation of this entity
   * @author denilssonmn
   */
  public PropertyGroupDocument toPropertyGroupDocument() {
    PropertyGroupDocument propertyGroupDocument = new PropertyGroupDocument();
    BeanUtils.copyProperties(this, propertyGroupDocument);
    propertyGroupDocument.setName(this.filename);
    if (Objects.nonNull(this.creationDate)) {
      propertyGroupDocument.setCreatedAt(this.creationDate.toLocalDate());
    }

    Optional.ofNullable(this.catalogDetail)
        .ifPresent(catalogDetailEntity ->
            propertyGroupDocument.setCatalogDetail(catalogDetailEntity.toCatalogDetail()));

    return propertyGroupDocument;
  }

}
