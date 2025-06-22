package es.miw.tfm.invierte.core.domain.service;

import es.miw.tfm.invierte.core.domain.model.Project;
import es.miw.tfm.invierte.core.domain.persistence.ProjectPersistence;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Service class for managing Project domain operations.
 * Delegates persistence operations to the {@link ProjectPersistence} interface.
 * Provides methods for creating, updating, and retrieving projects.
 *
 * @author denilssonmn
 */
@Service
@RequiredArgsConstructor
public class ProjectService {

  private final ProjectPersistence projectPersistence;

  public Mono<Project> create(Project project) {
    project.setDefaultValues();
    return this.projectPersistence.create(project);
  }

  public Mono<Project> update(Integer id, Project project) {
    return this.projectPersistence.update(id, project);
  }

  public Mono<Project> readById(Integer id) {
    return this.projectPersistence.readById(id);
  }
}
