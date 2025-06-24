package es.miw.tfm.invierte.core.infrastructure.data.dao;

import es.miw.tfm.invierte.core.infrastructure.data.entity.LandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing LandEntity objects.
 * <p>
 * Extends JpaRepository to provide CRUD operations and custom query
 * methods for LandEntity.
 * </p>
 *
 * @author denilssonmn
 * @see LandEntity
 */
public interface LandRepository extends JpaRepository<LandEntity, Integer> {

}
