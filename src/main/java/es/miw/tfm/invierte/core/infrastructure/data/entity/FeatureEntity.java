package es.miw.tfm.invierte.core.infrastructure.data.entity;

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

/**
 * JPA entity representing a feature that can be associated with property groups.
 * Maps the domain model to the database table `feature`.
 * Stores information about the feature's code, name, and its associations with
 * property group features.
 *
 * @author denilssonmn
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "feature")
public class FeatureEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 5)
  private String code;

  @Column(length = 100)
  private String name;

  @OneToMany(mappedBy = "feature", cascade = CascadeType.ALL, orphanRemoval = true)
  List<PropertyGroupFeatureEntity> propertyGroupFeatureEntities;

}
