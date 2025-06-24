package es.miw.tfm.invierte.core.infrastructure.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
