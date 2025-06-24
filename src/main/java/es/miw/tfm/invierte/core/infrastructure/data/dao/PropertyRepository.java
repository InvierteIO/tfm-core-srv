package es.miw.tfm.invierte.core.infrastructure.data.dao;

import es.miw.tfm.invierte.core.infrastructure.data.entity.PropertyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing PropertyEntity objects.
 * <p>
 * Extends JpaRepository to provide CRUD operations and custom query
 * methods for PropertyEntity.
 * </p>
 *
 * @author denilssonmn
 * @see PropertyEntity
 */
public interface PropertyRepository extends JpaRepository<PropertyEntity, Integer> {

}
