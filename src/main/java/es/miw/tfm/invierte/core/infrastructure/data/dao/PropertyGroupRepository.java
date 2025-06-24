package es.miw.tfm.invierte.core.infrastructure.data.dao;

import es.miw.tfm.invierte.core.infrastructure.data.entity.PropertyGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing PropertyGroupEntity objects.
 * <p>
 * Extends JpaRepository to provide CRUD operations and custom query
 * methods for PropertyGroupEntity.
 * </p>
 *
 * @author denilssonmn
 * @see PropertyGroupEntity
 */
public interface PropertyGroupRepository extends JpaRepository<PropertyGroupEntity, Integer> {

}
