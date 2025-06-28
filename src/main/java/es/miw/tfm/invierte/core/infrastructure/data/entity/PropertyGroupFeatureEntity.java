package es.miw.tfm.invierte.core.infrastructure.data.entity;

import es.miw.tfm.invierte.core.domain.model.PropertyFeature;
import es.miw.tfm.invierte.core.domain.model.enums.Flag;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import org.springframework.beans.BeanUtils;

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
  @Enumerated(EnumType.STRING)
  private Flag featureValue;

  @ManyToOne
  @JoinColumn(name = "property_group_id", referencedColumnName = "id")
  private PropertyGroupEntity propertyGroup;

  @ManyToOne
  @JoinColumn(name = "feature_id", referencedColumnName = "id")
  private FeatureEntity feature;

  /**
   * Converts this entity to its corresponding domain model
   * {@link PropertyFeature}. Copies properties and sets the feature
   * association.
   *
   * @return the mapped {@link PropertyFeature} domain object
   * @author denilssonmn
   */
  public PropertyFeature toPropertyFeature() {
    PropertyFeature propertyFeature = new PropertyFeature();
    BeanUtils.copyProperties(this, propertyFeature);
    propertyFeature.setFeature(this.feature.toFeature());
    return propertyFeature;
  }
}
