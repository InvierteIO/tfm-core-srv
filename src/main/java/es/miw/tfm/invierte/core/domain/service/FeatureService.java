package es.miw.tfm.invierte.core.domain.service;

import es.miw.tfm.invierte.core.domain.model.Feature;
import es.miw.tfm.invierte.core.domain.persistence.FeaturePersistence;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Service class for managing {@link Feature} domain operations.
 * Delegates persistence operations to the {@link FeaturePersistence} interface.
 * Provides reactive access to feature data.
 *
 * @author denilssonmn
 */
@Service
@RequiredArgsConstructor
public class FeatureService {

  private final FeaturePersistence featurePersistence;

  public Flux<Feature> readAll() {
    return this.featurePersistence.readAll();
  }

}
