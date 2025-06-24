package es.miw.tfm.invierte.core.infrastructure.data.entity;

import es.miw.tfm.invierte.core.domain.model.enums.BlockLocation;
import es.miw.tfm.invierte.core.domain.model.enums.RoadWay;
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
 * JPA entity representing a land record in the system.
 * Maps the domain model to the database table `land`.
 * Stores information about the block location and road way associated with the land.
 *
 * @author denilssonmn
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "land")
public class LandEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "block_location", length = 10)
  @Enumerated(EnumType.STRING)
  private BlockLocation blockLocation;

  @Column(name = "road_way", length = 10)
  @Enumerated(EnumType.STRING)
  private RoadWay roadWay;

}
