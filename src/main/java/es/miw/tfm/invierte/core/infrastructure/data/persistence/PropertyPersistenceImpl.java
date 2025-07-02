package es.miw.tfm.invierte.core.infrastructure.data.persistence;

import es.miw.tfm.invierte.core.domain.model.Property;
import es.miw.tfm.invierte.core.domain.persistence.PropertyPersistence;
import es.miw.tfm.invierte.core.infrastructure.data.dao.PropertyRepository;
import es.miw.tfm.invierte.core.infrastructure.data.entity.PropertyEntity;
import es.miw.tfm.invierte.core.infrastructure.data.entity.SubProjectPropertyGroupEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementation of the {@link PropertyPersistence} interface for managing
 * {@link Property} domain objects using Spring Data JPA repositories.
 * Handles creation, deletion, and retrieval of properties, mapping between
 * domain models and JPA entities.
 *
 * @author denilssonmn
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class PropertyPersistenceImpl implements PropertyPersistence {

  private final PropertyRepository propertyRepository;

  @Override
  public Mono<Property> create(Property property, Integer subProjectPropertyGroupId) {
    SubProjectPropertyGroupEntity subProjectPropertyGroupEntity =
        new SubProjectPropertyGroupEntity();
    subProjectPropertyGroupEntity.setId(subProjectPropertyGroupId);
    final var propertyEntity = new PropertyEntity(property, subProjectPropertyGroupEntity);
    return Mono.just(this.propertyRepository.save(propertyEntity)
        .toProperty());
  }

  @Override
  public Mono<Void> delete(Property property) {
    final var propertyEntity = new PropertyEntity(property);
    return Mono.fromRunnable(() -> {
      propertyEntity.setSubProjectPropertyGroup(null);
      this.propertyRepository.save(propertyEntity);
      this.propertyRepository.delete(propertyEntity);
    });
  }

  @Override
  public Flux<Property> findBySubProjectPropertyGroupId(Integer subProjectPropertyGroupId) {
    return Flux.fromIterable(
      this.propertyRepository.findBySubProjectPropertyGroupId(subProjectPropertyGroupId))
        .map(PropertyEntity::toProperty);
  }

}
