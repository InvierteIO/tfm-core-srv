package es.miw.tfm.invierte.core.domain.service;

import es.miw.tfm.invierte.core.domain.model.LocationCode;
import es.miw.tfm.invierte.core.domain.persistence.LocationCodePersistence;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Service class for managing location codes. Delegates persistence
 * operations to the corresponding persistence interface and provides
 * reactive access to location code domain objects.
 *
 * @author denilssonmn
 */
@Service
@RequiredArgsConstructor
public class LocationCodeService {

  private final LocationCodePersistence locationCodePersistence;

  public Flux<LocationCode> readAll() {
    return this.locationCodePersistence.readAll();
  }

}
