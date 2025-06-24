package es.miw.tfm.invierte.core.infrastructure.data.dao;

import es.miw.tfm.invierte.core.infrastructure.data.entity.HouseFloorAreaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing HouseFloorAreaEntity objects.
 * <p>
 * Extends JpaRepository to provide CRUD operations and custom query
 * methods for HouseFloorAreaEntity.
 * </p>
 *
 * @author denilssonmn
 * @see HouseFloorAreaEntity
 */
public interface HouseFloorAreaRepository extends JpaRepository<HouseFloorAreaEntity, Integer> {

}
