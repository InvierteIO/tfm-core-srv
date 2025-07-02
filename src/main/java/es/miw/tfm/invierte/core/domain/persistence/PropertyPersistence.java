package es.miw.tfm.invierte.core.domain.persistence;

import es.miw.tfm.invierte.core.domain.model.Property;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Persistence interface for managing {@link Property} domain objects.
 * Defines methods for creating, deleting, and retrieving properties,
 * typically implemented using reactive repositories.
 *
 * @author denilssonmn
 */
@Repository
public interface PropertyPersistence {

  Mono<Property> create(Property property, Integer subProjectPropertyGroupId);

  Mono<Void> delete(Property property);

  Flux<Property> findBySubProjectPropertyGroupId(Integer subProjectPropertyGroupId);
}
