package es.miw.tfm.invierte.core.infrastructure.data.entity;

import es.miw.tfm.invierte.core.domain.model.Property;
import es.miw.tfm.invierte.core.domain.model.enums.CommercializationCycle;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
 * JPA entity representing a property in the system.
 * Maps the domain model to the database table `property`.
 * Stores information about the property's codes, name, sale availability,
 * price, commercialization cycle, parking space, address, and associated
 * property documents.
 *
 * @author denilssonmn
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "property")
public class PropertyEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "code_system", length = 8, nullable = false)
  private String codeSystem;

  @Column(name = "code_enterprise", length = 10, nullable = false)
  private String codeEnterprise;

  @Column(length = 100, nullable = false)
  private String name;

  @Column(name = "available_sale", nullable = false)
  private boolean isAvailableSale;

  @Column(nullable = false, length = 10, precision = 2)
  private Double price;

  @Column(name = "commercialization_cycle", nullable = false, length = 12)
  @Enumerated(EnumType.STRING)
  private CommercializationCycle commercializationCycle;

  @Column(name = "parking_space", nullable = false)
  private boolean isParkingSpace;

  @Column(length = 200, nullable = false)
  private String address;

  @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PropertyDocumentEntity> propertyDocumentEntities;

  @ManyToOne
  @JoinColumn(name = "subproject_property_group_id", referencedColumnName = "id")
  private SubProjectPropertyGroupEntity subProjectPropertyGroup;

  public PropertyEntity(Property property,
      SubProjectPropertyGroupEntity subProjectPropertyGroupEntity) {
    BeanUtils.copyProperties(property, this);
    this.subProjectPropertyGroup = subProjectPropertyGroupEntity;
  }

  public PropertyEntity(Property property) {
    BeanUtils.copyProperties(property, this);
  }

  /**
   * Converts this entity to its corresponding domain model {@link Property}.
   * Copies all matching properties from the entity to the domain model.
   *
   * @return the domain model representation of this entity
   * @author denilssonmn
   */
  public Property toProperty() {
    Property property = new Property();
    BeanUtils.copyProperties(this, property);
    return property;
  }
}
