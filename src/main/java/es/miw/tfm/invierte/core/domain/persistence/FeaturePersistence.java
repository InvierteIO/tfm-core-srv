package es.miw.tfm.invierte.core.domain.persistence;

import es.miw.tfm.invierte.core.domain.model.Feature;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Persistence interface for managing {@link Feature} entities.
 * Defines methods for retrieving feature data from the underlying data source.
 * Provides reactive access to feature records.
 *
 * @author denilssonmn
 */
@Repository
public interface FeaturePersistence {

  Flux<Feature> readAll();

}
