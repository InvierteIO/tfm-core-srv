package es.miw.tfm.invierte.core.infrastructure.data.entity;

import es.miw.tfm.invierte.core.domain.model.SubProjectPropertyGroup;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * JPA entity representing the association between a subproject and a property group.
 * Maps the domain model to the database table `subproject_property_group`.
 * Stores information about the creation date and its relation to subprojects and property groups.
 *
 * @author denilssonmn
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subproject_property_group")
public class SubProjectPropertyGroupEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false)
  private LocalDateTime creationDate;

  @ManyToOne
  @JoinColumn(name = "sub_project_id", referencedColumnName = "id")
  private SubProjectEntity subProject;

  @ManyToOne
  @JoinColumn(name = "property_group_id", referencedColumnName = "id")
  private PropertyGroupEntity propertyGroup;

  @OneToMany(mappedBy = "subProjectPropertyGroup", fetch = FetchType.EAGER,
      cascade = CascadeType.REMOVE, orphanRemoval = true)
  private List<PropertyGroupDocumentEntity> propertyGroupDocumentEntities = new ArrayList<>();

  @OneToMany(mappedBy = "subProjectPropertyGroup", fetch = FetchType.EAGER,
      cascade = CascadeType.REMOVE, orphanRemoval = true)
  private List<PropertyEntity> propertyEntities = new ArrayList<>();


  /**
   * Constructs a SubProjectPropertyGroupEntity associating a subproject with a property group.
   * Initializes the creation date to the current time and sets the related entities.
   *
   * @param subProject the subproject entity to associate
   * @param propertyGroup the property group entity to associate
   */
  public SubProjectPropertyGroupEntity(SubProjectEntity subProject,
      PropertyGroupEntity propertyGroup) {
    this.creationDate = LocalDateTime.now();
    this.subProject = subProject;
    this.propertyGroup = propertyGroup;
  }

  /**
   * Converts this entity to its corresponding domain model
   * {@link SubProjectPropertyGroup}.
   *
   * @return the domain model representation of this entity
   */
  public SubProjectPropertyGroup toSubProjectPropertyGroup() {
    SubProjectPropertyGroup subProjectPropertyGroup = new SubProjectPropertyGroup();
    BeanUtils.copyProperties(this, subProjectPropertyGroup);

    Optional.ofNullable(this.subProject)
        .map(SubProjectEntity::toProjectStage)
        .ifPresent(subProjectPropertyGroup::setStage);

    Optional.ofNullable(this.propertyGroup)
        .map(PropertyGroupEntity::toPropertyGroup)
        .ifPresent(subProjectPropertyGroup::setPropertyGroup);

    Optional.ofNullable(this.propertyGroupDocumentEntities)
        .orElseGet(ArrayList::new)
        .forEach(propertyGroupDocumentEntity ->
          subProjectPropertyGroup.getPropertyGroupDocuments().add(
              propertyGroupDocumentEntity.toPropertyGroupDocument())
      );

    Optional.ofNullable(this.propertyEntities)
        .orElseGet(ArrayList::new)
        .forEach(propertyEntity ->
          subProjectPropertyGroup.getProperties().add(
              propertyEntity.toProperty())
      );

    return subProjectPropertyGroup;
  }
}
