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
 * JPA entity representing a document associated with a subproject in the system.
 * Maps the domain model to the database table `subproject_document`.
 * Stores information about the document's filename, name, path, creation date,
 * description, and its associated subproject.
 *
 * @author denilssonmn
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subproject_document")
public class SubProjectDocumentEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 100, nullable = false)
  private String filename;

  @Column(length = 100, nullable = false)
  private String name;

  @Column(length = 200, nullable = false)
  private String path;

  @Column(name = "creation_date", nullable = false)
  private LocalDateTime creationDate;

  @Column(length = 200, nullable = false)
  private String description;

  @ManyToOne
  @JoinColumn(name = "subproject_id", referencedColumnName = "id")
  private SubProjectEntity subProject;

}
