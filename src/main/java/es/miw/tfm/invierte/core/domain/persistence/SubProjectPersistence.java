package es.miw.tfm.invierte.core.domain.persistence;

import es.miw.tfm.invierte.core.domain.model.ProjectStage;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * Defines persistence operations for SubProject entities.
 *
 * @author denilssonmn
 */

@Repository
public interface SubProjectPersistence {

  Mono<ProjectStage> readById(Integer id);

}
