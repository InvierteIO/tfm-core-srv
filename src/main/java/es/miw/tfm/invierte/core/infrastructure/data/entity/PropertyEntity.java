package es.miw.tfm.invierte.core.infrastructure.data.entity;

import es.miw.tfm.invierte.core.domain.model.enums.CommercializationCycle;
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
  private boolean availableSale;

  @Column(nullable = false, length = 10, precision = 2)
  private Double price;

  @Column(name = "commercialization_cycle", nullable = false, length = 12)
  @Enumerated(EnumType.STRING)
  private CommercializationCycle commercializationCycle;

  @Column(name = "parking_space", nullable = false)
  private boolean parkingSpace;

  @Column(length = 200, nullable = false)
  private String address;

  @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PropertyDocumentEntity> propertyDocumentEntities;

}
