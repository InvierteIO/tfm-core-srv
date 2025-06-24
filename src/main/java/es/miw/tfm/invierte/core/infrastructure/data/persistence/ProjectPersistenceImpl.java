package es.miw.tfm.invierte.core.infrastructure.data.persistence;

import es.miw.tfm.invierte.core.domain.exception.NotFoundException;
import es.miw.tfm.invierte.core.domain.model.Project;
import es.miw.tfm.invierte.core.domain.persistence.ProjectPersistence;
import es.miw.tfm.invierte.core.infrastructure.data.dao.ProjectRepository;
import es.miw.tfm.invierte.core.infrastructure.data.entity.ProjectEntity;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

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

}
