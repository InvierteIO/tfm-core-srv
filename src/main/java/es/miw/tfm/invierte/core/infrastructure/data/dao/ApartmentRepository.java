package es.miw.tfm.invierte.core.infrastructure.data.dao;

import es.miw.tfm.invierte.core.infrastructure.data.entity.ApartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing ApartmentEntity objects.
 * <p>
 * Extends JpaRepository to provide CRUD operations and custom query
 * methods for ApartmentEntity.
 * </p>
 *
 * @author denilssonmn
 * @see ApartmentEntity
 */
public interface ApartmentRepository extends JpaRepository<ApartmentEntity, Integer> {

}
