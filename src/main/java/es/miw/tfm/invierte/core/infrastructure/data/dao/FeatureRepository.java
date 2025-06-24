package es.miw.tfm.invierte.core.infrastructure.data.dao;

import es.miw.tfm.invierte.core.infrastructure.data.entity.FeatureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing FeatureEntity objects.
 * <p>
 * Extends JpaRepository to provide CRUD operations and custom query
 * methods for FeatureEntity.
 * </p>
 *
 * @author denilssonmn
 * @see FeatureEntity
 */
public interface FeatureRepository extends JpaRepository<FeatureEntity, Integer> {
}
