package es.miw.tfm.invierte.core.infrastructure.data.dao;

import es.miw.tfm.invierte.core.infrastructure.data.entity.HouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing HouseEntity objects.
 * <p>
 * Extends JpaRepository to provide CRUD operations and custom query
 * methods for HouseEntity.
 * </p>
 *
 * @author denilssonmn
 * @see HouseEntity
 */
public interface HouseRepository extends JpaRepository<HouseEntity, Integer> {

}
