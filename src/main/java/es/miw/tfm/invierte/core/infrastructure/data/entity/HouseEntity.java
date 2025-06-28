package es.miw.tfm.invierte.core.infrastructure.data.entity;

import es.miw.tfm.invierte.core.domain.model.enums.BlockLocation;
import es.miw.tfm.invierte.core.domain.model.enums.RoadWay;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JPA entity representing a house in the system.
 * Maps the domain model to the database table `house`.
 * Stores information about the house's roofed area, total bathrooms, rooms,
 * floors, block location, road way, and its associated floor areas.
 *
 * @author denilssonmn
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "house")
public class HouseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "roofed_area")
  private Integer roofedArea;

  @Column(name = "total_bathrooms")
  private Integer totalBathrooms;

  @Column(name = "total_rooms")
  private Integer totalRooms;

  @Column(name = "total_floors")
  private Integer totalFloors;

  @Column(name = "block_location", length = 10)
  @Enumerated(EnumType.STRING)
  private BlockLocation blockLocation;

  @Column(name = "road_way", length = 10)
  @Enumerated(EnumType.STRING)
  private RoadWay roadWay;

  @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true,
      fetch = FetchType.EAGER)
  List<HouseFloorAreaEntity> houseFloorAreaEntities = new ArrayList<>();

}
