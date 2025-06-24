package es.miw.tfm.invierte.core.infrastructure.data.dao;

import es.miw.tfm.invierte.core.infrastructure.data.entity.PropertyGroupFeatureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing PropertyGroupFeatureEntity objects.
 * <p>
 * Extends JpaRepository to provide CRUD operations and custom query
 * methods for PropertyGroupFeatureEntity.
 * </p>
 *
 * @author denilssonmn
 * @see PropertyGroupFeatureEntity
 */
public interface PropertyGroupFeatureRepository
    extends JpaRepository<PropertyGroupFeatureEntity, Integer> {

}
