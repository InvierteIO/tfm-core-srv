package es.miw.tfm.invierte.core.infrastructure.data.entity;

import es.miw.tfm.invierte.core.domain.model.enums.DepartmentType;
import es.miw.tfm.invierte.core.domain.model.enums.TowerLocation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JPA entity representing an apartment (department) in the system.
 * Maps the domain model to the database table `department`.
 * Stores information about the apartment's total bathrooms, rooms, type,
 * floor areas, and tower location.
 *
 * @author denilssonmn
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "department")
public class ApartmentEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "total_bathrooms")
  private Integer totalBathrooms;

  @Column(name = "total_rooms")
  private Integer totalRooms;

  @Column(length = 10)
  @Enumerated(EnumType.STRING)
  private DepartmentType type;

  @Column(name = "area_floor_one")
  private Double areaFloorOne;

  @Column(name = "area_floor_two")
  private Double areaFloorTwo;

  @Column(name = "area_floor_three")
  private Double areaFloorThree;

  @Column(name = "tower_location", length = 10)
  @Enumerated(EnumType.STRING)
  private TowerLocation towerLocation;

}
