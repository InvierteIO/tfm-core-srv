package es.miw.tfm.invierte.core.infrastructure.data.dao;

import es.miw.tfm.invierte.core.infrastructure.data.entity.LocationCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing LocationCodeEntity objects.
 * <p>
 * Extends JpaRepository to provide CRUD operations and custom query
 * methods for LocationCodeEntity.
 * </p>
 *
 * @author denilssonmn
 * @see LocationCodeEntity
 */
public interface LocationCodeRepository extends JpaRepository<LocationCodeEntity, Integer> {

}
