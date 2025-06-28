package es.miw.tfm.invierte.core.infrastructure.data.persistence;

import es.miw.tfm.invierte.core.domain.exception.NotFoundException;
import es.miw.tfm.invierte.core.domain.model.PropertyGroup;
import es.miw.tfm.invierte.core.domain.persistence.PropertyGroupPersistence;
import es.miw.tfm.invierte.core.infrastructure.data.dao.PropertyGroupRepository;
import es.miw.tfm.invierte.core.infrastructure.data.entity.PropertyGroupEntity;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

/**
 * Implementation of the {@link PropertyGroupPersistence} interface.
 * Handles persistence operations for property groups using the
 * {@link PropertyGroupRepository}. Maps between domain models and
 * entity representations, providing create, update, and read operations.
 *
 * @author denilssonmn
 */

@Repository
@RequiredArgsConstructor
@Slf4j
public class PropertyGroupPersistenceImpl implements PropertyGroupPersistence {

  private final PropertyGroupRepository propertyGroupRepository;

  @Override
  public Mono<PropertyGroup> create(PropertyGroup propertyGroup) {
    final var propertyGroupEntity = new PropertyGroupEntity(propertyGroup);
    return Mono.just(this.propertyGroupRepository.save(propertyGroupEntity)
        .toPropertyGroup());
  }

  @Override
  public Mono<PropertyGroup> update(Integer id, PropertyGroup propertyGroup) {
    return Mono.just(this.propertyGroupRepository.findById(id))
        .switchIfEmpty(Mono.error(
            new NotFoundException("Non existent PropertyGroup with id: " + id)))
        .flatMap(existingEntity -> {
          if (existingEntity.isEmpty()) {
            return Mono.error(new NotFoundException("Non existent PropertyGroup with id: " + id));
          }
          PropertyGroupEntity propertyGroupEntityDb = new PropertyGroupEntity(propertyGroup);
          propertyGroupEntityDb.setId(existingEntity.get().getId());
          return Mono.just(this.propertyGroupRepository.save(propertyGroupEntityDb));
        })
        .map(PropertyGroupEntity::toPropertyGroup);
  }

  @Override
  @Transactional(readOnly = true)
  public Mono<PropertyGroup> readById(Integer id) {
    return Mono.just(this.propertyGroupRepository.findById(id))
        .switchIfEmpty(Mono.error(
            new NotFoundException("Non existent PropertyGroup with id: " + id)))
        .map(Optional::get)
        .map(PropertyGroupEntity::toPropertyGroup);
  }
}
