package es.miw.tfm.invierte.core.infrastructure.data.entity;

import es.miw.tfm.invierte.core.domain.model.enums.Currency;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JPA entity representing a group of properties in the system.
 * Maps the domain model to the database table `property_group`.
 * Stores information about the group's name, perimeter, area, price, interest rate,
 * currency, and related entities such as apartment, land, house, features,
 * and subproject associations.
 *
 * @author denilssonmn
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "property_group")
public class PropertyGroupEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 200, nullable = false)
  private String name;

  @Column(nullable = false)
  private Double perimeter;

  @Column(nullable = false)
  private Double area;

  @Column(name = "parking_space", nullable = false)
  private boolean parkingSpace;

  @Column(name = "front_park", nullable = false)
  private boolean frontPark;

  @Column(nullable = false, length = 10, precision = 2)
  private BigDecimal price;

  @Column(name = "annual_interest_rate", nullable = false, length = 10, precision = 2)
  private Double annualInterestRate;

  @Column(length = 3)
  @Enumerated(EnumType.STRING)
  private Currency currency;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "apartment_id", referencedColumnName = "id")
  private ApartmentEntity apartment;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "land_id", referencedColumnName = "id")
  private LandEntity land;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "house_id", referencedColumnName = "id")
  private HouseEntity house;

  @OneToMany(mappedBy = "propertyGroup", cascade = CascadeType.ALL, orphanRemoval = true)
  List<PropertyGroupFeatureEntity> propertyGroupFeatureEntities;

  @OneToMany(mappedBy = "propertyGroup", cascade = CascadeType.ALL, orphanRemoval = true)
  List<SubProjectPropertyGroupEntity> subProjectPropertyGroups;

}
