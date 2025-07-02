package es.miw.tfm.invierte.core.infrastructure.data.entity;

import es.miw.tfm.invierte.core.domain.model.ProjectDocument;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * JPA entity representing a document associated with a project in the system.
 * Maps the domain model to the database table `project_document`.
 * Stores information about the document's filename, path, creation date,
 * description, and its associated project.
 *
 * @author denilssonmn
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project_document")
public class ProjectDocumentEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 100, nullable = false)
  private String filename;

  @Column(length = 200, nullable = false)
  private String path;

  @Column(name = "creation_date", nullable = false)
  private LocalDateTime creationDate;

  @Column(length = 200, nullable = false)
  private String description;

  @ManyToOne
  @JoinColumn(name = "project_id", referencedColumnName = "id")
  private ProjectEntity project;

  @ManyToOne
  @JoinColumn(name = "catalog_detail_id", referencedColumnName = "id")
  private CatalogDetailEntity catalogDetail;

  /**
   * Constructs a new ProjectDocumentEntity from the given domain model and
   * associated project entity. Copies all matching properties from the
   * ProjectDocument and sets the creation date to now.
   *
   * @param projectDocument the domain model representing the document
   * @param projectEntity the associated project entity
   */
  public ProjectDocumentEntity(ProjectDocument projectDocument, ProjectEntity projectEntity,
      CatalogDetailEntity catalogDetailEntity) {
    BeanUtils.copyProperties(projectDocument, this);
    this.creationDate = LocalDateTime.now();
    this.project = projectEntity;
    this.catalogDetail = catalogDetailEntity;
  }

  /**
   * Converts this entity to its corresponding domain model object.
   * Copies all matching properties from the entity to a new
   * ProjectDocument instance.
   *
   * @return the domain model representation of this project document entity
   */
  public ProjectDocument toProjectDocument() {
    ProjectDocument projectDocument = new ProjectDocument();
    BeanUtils.copyProperties(this, projectDocument);
    projectDocument.setName(this.filename);
    if (Objects.nonNull(this.creationDate)) {
      projectDocument.setCreatedAt(this.creationDate.toLocalDate());
    }

    Optional.ofNullable(this.catalogDetail)
        .ifPresent(catalogDetailEntity ->
            projectDocument.setCatalogDetail(catalogDetailEntity.toCatalogDetail()));

    return projectDocument;
  }
}
