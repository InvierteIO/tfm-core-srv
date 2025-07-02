package es.miw.tfm.invierte.core.infrastructure.data.entity;

import es.miw.tfm.invierte.core.domain.model.HouseFloorArea;
import es.miw.tfm.invierte.core.domain.model.PropertyGroup;
import es.miw.tfm.invierte.core.domain.model.enums.Currency;
import es.miw.tfm.invierte.core.domain.model.enums.Flag;
import es.miw.tfm.invierte.core.domain.model.enums.PropertyCategory;
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
import java.util.Objects;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

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
  private Double price;

  @Column(name = "annual_interest_rate", nullable = false, length = 10, precision = 2)
  private Double annualInterestRate;

  @Column(length = 3)
  @Enumerated(EnumType.STRING)
  private Currency currency;

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "apartment_id", referencedColumnName = "id")
  private ApartmentEntity apartment;

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "land_id", referencedColumnName = "id")
  private LandEntity land;

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "house_id", referencedColumnName = "id")
  private HouseEntity house;

  @OneToMany(mappedBy = "propertyGroup", cascade = CascadeType.ALL, orphanRemoval = true,
      fetch = FetchType.EAGER)
  List<PropertyGroupFeatureEntity> propertyGroupFeatureEntities = new ArrayList<>();

  @OneToMany(mappedBy = "propertyGroup", cascade = CascadeType.ALL)
  List<SubProjectPropertyGroupEntity> subProjectPropertyGroups;

  /**
   * Constructs a new entity from the given domain model
   * {@link PropertyGroup}.
   * Copies relevant properties and initializes related entities
   * such as apartment, land, and house based on the property category.
   *
   * @param propertyGroup the domain model to map from
   */
  public PropertyGroupEntity(PropertyGroup propertyGroup) {
    BeanUtils.copyProperties(propertyGroup, this);
    this.parkingSpace = Objects.nonNull(propertyGroup.getParkingSpace())
        && !Flag.NO.equals(propertyGroup.getParkingSpace());
    this.frontPark = Objects.nonNull(propertyGroup.getFrontPark())
        && !Flag.NO.equals(propertyGroup.getFrontPark());

    Optional.ofNullable(propertyGroup.getPropertyFeatures())
        .orElseGet(ArrayList::new)
        .forEach(propertyFeature -> {
          PropertyGroupFeatureEntity propertyGroupFeatureEntity = new PropertyGroupFeatureEntity();
          BeanUtils.copyProperties(propertyFeature, propertyGroupFeatureEntity);

          FeatureEntity featureEntity = new FeatureEntity();
          BeanUtils.copyProperties(propertyFeature.getFeature(), featureEntity);

          propertyGroupFeatureEntity.setPropertyGroup(this);
          propertyGroupFeatureEntity.setFeature(featureEntity);

          propertyGroupFeatureEntity.setId(null);
          this.propertyGroupFeatureEntities.add(propertyGroupFeatureEntity);
        });

    this.copyApartmentValuesFromModel(propertyGroup);
    this.copyLandValuesFromModel(propertyGroup);
    this.copyHouseValuesFromModel(propertyGroup);
  }

  /**
   * Copies house-specific values from the given domain model
   * {@link PropertyGroup} to this entity.
   * Initializes the {@link HouseEntity} and its floor areas if the property
   * category is HOUSE.
   *
   * @param propertyGroup the domain model to copy values from
   */
  private void copyHouseValuesFromModel(PropertyGroup propertyGroup) {
    if (PropertyCategory.HOUSE.equals(propertyGroup.getPropertyCategory())) {
      HouseEntity houseEntity = new HouseEntity();
      BeanUtils.copyProperties(propertyGroup, houseEntity);
      houseEntity.setId(null);

      Optional.ofNullable(propertyGroup.getHouseFloorAreas())
          .orElseGet(ArrayList::new)
          .forEach(houseFloorArea -> {
            HouseFloorAreaEntity houseFloorAreaEntity = new HouseFloorAreaEntity();
            BeanUtils.copyProperties(houseFloorArea, houseFloorAreaEntity);
            houseFloorAreaEntity.setHouse(houseEntity);
            houseFloorAreaEntity.setId(null);
            houseEntity.getHouseFloorAreaEntities().add(houseFloorAreaEntity);
          });
      this.house = houseEntity;
    }
  }

  private void copyLandValuesFromModel(PropertyGroup propertyGroup) {
    if (PropertyCategory.LAND.equals(propertyGroup.getPropertyCategory())) {
      LandEntity landEntity = new LandEntity();
      BeanUtils.copyProperties(propertyGroup, landEntity);
      landEntity.setId(null);
      this.land = landEntity;
    }
  }

  private void copyApartmentValuesFromModel(PropertyGroup propertyGroup) {
    if (PropertyCategory.APARTMENT.equals(propertyGroup.getPropertyCategory())) {
      ApartmentEntity apartmentEntity = new ApartmentEntity();
      BeanUtils.copyProperties(propertyGroup, apartmentEntity);
      apartmentEntity.setId(null);
      this.apartment = apartmentEntity;
    }
  }

  /**
   * Maps this entity to its corresponding domain model {@link PropertyGroup}.
   * Copies all relevant properties and initializes related domain objects
   * such as apartment, land, and house, including their nested attributes.
   *
   * @return the mapped {@link PropertyGroup} domain model
   */
  public PropertyGroup toPropertyGroup() {
    PropertyGroup propertyGroup = new PropertyGroup();
    BeanUtils.copyProperties(this, propertyGroup);
    this.copyApartmentValuesFromEntity(propertyGroup);
    this.copyLandValuesFromEntity(propertyGroup);
    this.copyHouseValuesFromEntity(propertyGroup);
    propertyGroup.setId(this.id);
    propertyGroup.setParkingSpace(this.parkingSpace ? Flag.YES : Flag.NO);
    propertyGroup.setFrontPark(this.frontPark ? Flag.YES : Flag.NO);

    Optional.ofNullable(this.propertyGroupFeatureEntities)
        .orElseGet(ArrayList::new)
        .forEach(propertyGroupFeatureEntity -> {
          propertyGroup.getPropertyFeatures().add(propertyGroupFeatureEntity.toPropertyFeature());
        });

    return propertyGroup;
  }

  private void copyHouseValuesFromEntity(PropertyGroup propertyGroup) {
    if (Objects.nonNull(this.house)) {
      propertyGroup.setPropertyCategory(PropertyCategory.HOUSE);
      BeanUtils.copyProperties(this.house, propertyGroup);

      Optional.ofNullable(this.house.getHouseFloorAreaEntities())
          .orElseGet(ArrayList::new)
          .forEach(houseFloorAreaEntity -> {
            HouseFloorArea houseFloorArea = new HouseFloorArea();
            BeanUtils.copyProperties(houseFloorAreaEntity, houseFloorArea);
            propertyGroup.getHouseFloorAreas().add(houseFloorArea);
          });
    }
  }

  private void copyLandValuesFromEntity(PropertyGroup propertyGroup) {
    if (Objects.nonNull(this.land)) {
      propertyGroup.setPropertyCategory(PropertyCategory.LAND);
      BeanUtils.copyProperties(this.land, propertyGroup);
    }
  }

  private void copyApartmentValuesFromEntity(PropertyGroup propertyGroup) {
    if (Objects.nonNull(this.apartment)) {
      propertyGroup.setPropertyCategory(PropertyCategory.APARTMENT);
      BeanUtils.copyProperties(this.apartment, propertyGroup);
    }
  }

}
