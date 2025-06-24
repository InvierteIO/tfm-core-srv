package es.miw.tfm.invierte.core.infrastructure.data.dao;

import es.miw.tfm.invierte.core.infrastructure.data.entity.FinancialBonusTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing FinancialBonusTypeEntity objects.
 * <p>
 * Extends JpaRepository to provide CRUD operations and custom query
 * methods for FinancialBonusTypeEntity.
 * </p>
 *
 * @author denilssonmn
 * @see FinancialBonusTypeEntity
 */
public interface FinancialBonusTypeRepository
    extends JpaRepository<FinancialBonusTypeEntity, Integer> {

}
