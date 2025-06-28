package es.miw.tfm.invierte.core.domain.persistence;

import es.miw.tfm.invierte.core.domain.model.LocationCode;
import reactor.core.publisher.Flux;

/**
 * Interface for location code persistence operations. Provides methods
 * to read location code domain objects in a reactive manner.
 *
 * @author denilssonmn
 */
public interface LocationCodePersistence {

  Flux<LocationCode> readAll();

}
