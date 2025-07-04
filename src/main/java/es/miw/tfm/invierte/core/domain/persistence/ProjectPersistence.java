package es.miw.tfm.invierte.core.domain.persistence;

import es.miw.tfm.invierte.core.domain.model.Project;
import es.miw.tfm.invierte.core.domain.model.ProjectDocument;
import jakarta.validation.Valid;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Interface for Project persistence operations.
 * Provides methods for creating, updating, deleting, and retrieving Project entities.
 * This interface is implemented by classes that handle data access for Projects.
 *
 * @author denilssonmn
 * @author devcastlecix
 */

@Repository
public interface ProjectPersistence {

  Mono<Project> create(Project project);

  Mono<Project> update(Integer id, Project project);

  Mono<Project> readById(Integer id);

  Mono<Project> readByTaxIdentificationNumberAndId(String taxIdentificationNumber,
      Integer projectId);

  Mono<ProjectDocument> createDocument(Integer projectId, @Valid ProjectDocument projectDocument);

  Mono<Void> deleteDocument(Integer documentId);

  Flux<Project> readAll();
}
