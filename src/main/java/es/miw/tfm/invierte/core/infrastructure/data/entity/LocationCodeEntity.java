package es.miw.tfm.invierte.core.infrastructure.data.entity;

import es.miw.tfm.invierte.core.domain.model.LocationCode;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import org.springframework.beans.BeanUtils;

/**
 * JPA entity representing a location code in the system.
 * Maps the domain model to the database table `location_code`.
 * Stores information about the code, type, name, and its association with subprojects.
 *
 * @author denilssonmn
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "location_code")
public class LocationCodeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 6, unique = true)
  private String code;

  @Column(length = 10)
  private String type;

  @Column(length = 50)
  private String name;

  @OneToMany(mappedBy = "locationCode", cascade = CascadeType.ALL, orphanRemoval = true)
  List<SubProjectEntity> subProjectEntities;

  public LocationCodeEntity(LocationCode locationCode) {
    BeanUtils.copyProperties(locationCode, this);
  }

  /**
   * Converts this entity to its corresponding domain model object.
   * Copies all matching properties from the entity to a new LocationCode
   * instance.
   *
   * @return the domain model representation of this location code entity
   */
  public LocationCode toLocationCode() {
    LocationCode locationCode = new LocationCode();
    BeanUtils.copyProperties(this, locationCode);
    return locationCode;
  }
}
