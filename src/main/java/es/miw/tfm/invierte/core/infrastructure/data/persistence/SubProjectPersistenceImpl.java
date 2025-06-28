package es.miw.tfm.invierte.core.infrastructure.data.persistence;

import es.miw.tfm.invierte.core.domain.exception.NotFoundException;
import es.miw.tfm.invierte.core.domain.model.ProjectStage;
import es.miw.tfm.invierte.core.domain.persistence.SubProjectPersistence;
import es.miw.tfm.invierte.core.infrastructure.data.dao.SubProjectRepository;
import es.miw.tfm.invierte.core.infrastructure.data.entity.SubProjectEntity;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * Implementation of the {@link SubProjectPersistence} interface.
 * Handles persistence operations for subprojects using the {@link SubProjectRepository}.
 * Maps between domain models and entity representations.
 *
 * @author denilssonmn
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class SubProjectPersistenceImpl implements SubProjectPersistence {

  private final SubProjectRepository subProjectRepository;

  @Override
  public Mono<ProjectStage> readById(Integer id) {
    return Mono.just(this.subProjectRepository.findById(id))
        .switchIfEmpty(Mono.error(new NotFoundException("Non existent SubProject with id: " + id)))
        .map(Optional::get)
        .map(SubProjectEntity::toProjectStage);
  }
}
