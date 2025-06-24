package es.miw.tfm.invierte.core.infrastructure.data.dao;

import es.miw.tfm.invierte.core.infrastructure.data.entity.SubProjectBonusTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing SubProjectBonusTypeEntity objects.
 * <p>
 * Extends JpaRepository to provide CRUD operations and custom query
 * methods for SubProjectBonusTypeEntity.
 * </p>
 *
 * @author denilssonmn
 * @see SubProjectBonusTypeEntity
 */
public interface SubProjectBonusTypeRepository
    extends JpaRepository<SubProjectBonusTypeEntity, Integer> {

}
