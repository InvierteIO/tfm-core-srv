package es.miw.tfm.invierte.core.infrastructure.data.persistence;

import es.miw.tfm.invierte.core.domain.model.Feature;
import es.miw.tfm.invierte.core.domain.persistence.FeaturePersistence;
import es.miw.tfm.invierte.core.infrastructure.data.dao.FeatureRepository;
import es.miw.tfm.invierte.core.infrastructure.data.entity.FeatureEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Implementation of the {@link FeaturePersistence} interface for managing feature entities.
 * Handles the persistence logic for retrieving all features using a JPA repository.
 * Converts between domain models and JPA entities.
 *
 * @author denilssonmn
 */
@Repository
@RequiredArgsConstructor
public class FeaturePersistenceImpl implements FeaturePersistence {

  private final FeatureRepository featureRepository;

  @Override
  public Flux<Feature> readAll() {
    return Flux.defer(() -> Flux.fromIterable(this.featureRepository.findAll())
        .map(FeatureEntity::toFeature));
  }
}
