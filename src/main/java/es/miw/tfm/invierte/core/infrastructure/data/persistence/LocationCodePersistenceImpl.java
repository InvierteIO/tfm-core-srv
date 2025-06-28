package es.miw.tfm.invierte.core.infrastructure.data.persistence;

import es.miw.tfm.invierte.core.domain.model.LocationCode;
import es.miw.tfm.invierte.core.domain.persistence.LocationCodePersistence;
import es.miw.tfm.invierte.core.infrastructure.data.dao.LocationCodeRepository;
import es.miw.tfm.invierte.core.infrastructure.data.entity.LocationCodeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Implementation of the LocationCodePersistence interface. Provides
 * reactive access to location code domain objects by delegating to the
 * underlying JPA repository.
 *
 * @author denilssonmn
 */
@Repository
@RequiredArgsConstructor
public class LocationCodePersistenceImpl implements LocationCodePersistence {

  private final LocationCodeRepository locationCodeRepository;

  @Override
  public Flux<LocationCode> readAll() {
    return Flux.defer(() -> Flux.fromIterable(this.locationCodeRepository.findAll()))
        .map(LocationCodeEntity::toLocationCode);
  }

}
