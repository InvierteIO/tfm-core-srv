package es.miw.tfm.invierte.core.domain.persistence;

import es.miw.tfm.invierte.core.domain.model.PropertyGroup;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * Defines persistence operations for PropertyGroup entities.
 *
 * @author denilssonmn
 */
@Repository
public interface PropertyGroupPersistence {

  Mono<PropertyGroup> create(PropertyGroup propertyGroup);

  Mono<PropertyGroup> update(Integer id, PropertyGroup propertyGroup);

  Mono<PropertyGroup> readById(Integer id);

}
