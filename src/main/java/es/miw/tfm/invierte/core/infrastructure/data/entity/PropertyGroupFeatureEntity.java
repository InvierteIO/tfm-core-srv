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
 * JPA entity representing the association between a property group and a feature.
 * Maps the domain model to the database table `property_group_feature`.
 * Stores information about the feature value and its relation to property groups and features.
 *
 * @author denilssonmn
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "property_group_feature")
public class PropertyGroupFeatureEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "feature_value", length = 50, nullable = false)
  private String featureValue;

  @ManyToOne
  @JoinColumn(name = "property_group_id", referencedColumnName = "id")
  private PropertyGroupEntity propertyGroup;

  @ManyToOne
  @JoinColumn(name = "feature_id", referencedColumnName = "id")
  private FeatureEntity feature;

}
