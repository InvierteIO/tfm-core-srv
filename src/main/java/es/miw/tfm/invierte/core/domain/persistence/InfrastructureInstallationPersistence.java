package es.miw.tfm.invierte.core.domain.persistence;

import es.miw.tfm.invierte.core.domain.model.InfraInstallation;
import reactor.core.publisher.Flux;

/**
 * Interface for infrastructure installation persistence operations.
 * Provides methods to read infrastructure installation domain objects
 * in a reactive manner.
 *
 * @author denilssonmn
 */
public interface InfrastructureInstallationPersistence {

  Flux<InfraInstallation> readAll();
}
