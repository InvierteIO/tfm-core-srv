package es.miw.tfm.invierte.core.infrastructure.data.entity;

import es.miw.tfm.invierte.core.domain.model.Project;
import es.miw.tfm.invierte.core.domain.model.enums.ProjectStatus;
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
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

/**
 * JPA entity representing a Project in the system.
 * Maps the domain model {@link Project} to the database table "project".
 * Provides conversion methods between the entity and the domain model.
 *
 * @author denilssonmn
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Slf4j
@Table(name = "project")
public class ProjectEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 100, nullable = false)
  private String name;

  @Column(name = "office_address", length = 200)
  private String officeAddress;

  @Column(name = "office_number", length = 6)
  private String officeNumber;

  @Column(length = 200)
  private String supervisor;

  @Column(length = 12)
  @Enumerated(EnumType.STRING)
  private ProjectStatus status;

  @Column(length = 500)
  private String description;

  private Integer stages;

  @Column(name = "tax_identification_number")
  private String taxIdentificationNumber;

  @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true,
      fetch = FetchType.EAGER)
  private List<SubProjectEntity> subProjectEntities = new ArrayList<>();

  @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
  private List<ProjectDocumentEntity> projectDocumentEntities = new ArrayList<>();

  /**
   * Constructs a ProjectEntity from a domain Project object.
   *
   * @param project the domain Project to copy properties from
   */
  public ProjectEntity(Project project) {
    BeanUtils.copyProperties(project, this);

    Optional.ofNullable(project.getProjectStages())
        .orElseGet(ArrayList::new)
            .forEach(projectStage ->
                this.subProjectEntities.add(new SubProjectEntity(projectStage, this)));
  }

  /**
   * Converts this entity to a domain Project object.
   *
   * @return the corresponding Project domain object
   */
  public Project toProject() {
    Project project = new Project();
    BeanUtils.copyProperties(this, project);

    Optional.ofNullable(this.subProjectEntities)
        .orElseGet(ArrayList::new)
        .forEach(projectStageEntity ->
          project.getProjectStages().add(projectStageEntity.toProjectStage()));

    Optional.ofNullable(this.projectDocumentEntities)
        .orElseGet(ArrayList::new)
        .forEach(projectDocumentEntity ->
            project.getProjectDocuments().add(projectDocumentEntity.toProjectDocument()));

    return project;
  }



}
