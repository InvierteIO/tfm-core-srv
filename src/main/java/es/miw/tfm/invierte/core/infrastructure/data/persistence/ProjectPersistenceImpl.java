package es.miw.tfm.invierte.core.infrastructure.data.persistence;

import es.miw.tfm.invierte.core.domain.exception.NotFoundException;
import es.miw.tfm.invierte.core.domain.model.Project;
import es.miw.tfm.invierte.core.domain.model.ProjectDocument;
import es.miw.tfm.invierte.core.domain.persistence.ProjectPersistence;
import es.miw.tfm.invierte.core.infrastructure.data.dao.CatalogDetailRepository;
import es.miw.tfm.invierte.core.infrastructure.data.dao.ProjectDocumentRepository;
import es.miw.tfm.invierte.core.infrastructure.data.dao.ProjectRepository;
import es.miw.tfm.invierte.core.infrastructure.data.entity.ProjectDocumentEntity;
import es.miw.tfm.invierte.core.infrastructure.data.entity.ProjectEntity;

import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * Implementation of the {@link ProjectPersistence} interface for managing Project entities.
 * Handles the persistence logic for creating, updating, and retrieving projects using
 * a JPA repository.
 * Converts between domain models and JPA entities, and manages exception handling for
 * conflicts and not found cases.
 *
 * @author denilssonmn
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class ProjectPersistenceImpl  implements ProjectPersistence {

  private final ProjectRepository projectRepository;

  private final ProjectDocumentRepository projectDocumentRepository;

  private final CatalogDetailRepository catalogDetailRepository;

  @Override
  public Mono<Project> create(Project project) {
    final var projectEntity = new ProjectEntity(project);
    return Mono.just(this.projectRepository.save(projectEntity)
        .toProject());
  }

  @Override
  @Transactional
  public Mono<Project> update(Integer id, Project project) {
    return Mono.just(this.projectRepository.findById(id))
        .switchIfEmpty(Mono.error(new NotFoundException("Non existent Project with id: " + id)))
        .flatMap(existingEntity -> {
          if (existingEntity.isEmpty()) {
            return Mono.error(new NotFoundException("Non existent Project with id: " + id));
          }
          ProjectEntity projectUpdateDb = new ProjectEntity(project);
          projectUpdateDb.setId(existingEntity.get().getId());
          return Mono.just(this.projectRepository.save(projectUpdateDb));
        })
        .map(ProjectEntity::toProject);
  }

  @Override
  @Transactional(readOnly = true)
  public Mono<Project> readById(Integer id) {
    return Mono.just(this.projectRepository.findById(id))
        .switchIfEmpty(Mono.error(new NotFoundException("Non existent Project with id: " + id)))
        .map(Optional::get)
        .map(ProjectEntity::toProject);
  }

  @Override
  public Mono<Project> readByTaxIdentificationNumberAndId(String taxIdentificationNumber,
      Integer projectId) {
    return Mono.just(this.projectRepository
            .findByTaxIdentificationNumberAndId(taxIdentificationNumber, projectId))
        .switchIfEmpty(Mono.error(
            new NotFoundException("Non existent Project with id: " + projectId
                + " for taxIdentificationNumber: " + taxIdentificationNumber)))
        .map(ProjectEntity::toProject);
  }

  @Override
  @Transactional
  public Mono<ProjectDocument> createDocument(Integer projectId, ProjectDocument projectDocument) {
    return Mono.just(this.projectRepository.findById(projectId))
        .switchIfEmpty(Mono.error(new NotFoundException("Non existent Project with id: "
            + projectId)))
        .flatMap(projectEntity -> {
          if (projectEntity.isEmpty()) {
            return Mono.error(new NotFoundException("Non existent Project with id: " + projectId));
          }
          final var catalogDetailCode = Objects.nonNull(projectDocument.getCatalogDetail())
              ? projectDocument.getCatalogDetail().getCode() : null;

          final var catalogDetail = this.catalogDetailRepository.findByCode(catalogDetailCode)
              .orElse(null);

          ProjectDocumentEntity projectDocumentEntity = new ProjectDocumentEntity(projectDocument,
              projectEntity.get(), catalogDetail);
          return Mono.just(this.projectDocumentRepository.save(projectDocumentEntity));
        })
        .map(ProjectDocumentEntity::toProjectDocument);
  }

  @Override
  @Transactional
  public Mono<Void> deleteDocument(Integer documentId) {
    return Mono.fromRunnable(() -> {
      this.projectDocumentRepository.deleteById(documentId);
    });
  }

}
