package es.miw.tfm.invierte.core.infrastructure.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JPA entity representing a floor area within a house.
 * Maps the domain model to the database table `house_floor_area`.
 * Stores information about the floor number, its area, and the associated house.
 *
 * @author denilssonmn
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "house_floor_area")
public class HouseFloorAreaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false)
  private Integer number;

  @Column(nullable = false)
  private Double area;

  @ManyToOne
  @JoinColumn(name = "house_id", referencedColumnName = "id")
  private HouseEntity house;
}
