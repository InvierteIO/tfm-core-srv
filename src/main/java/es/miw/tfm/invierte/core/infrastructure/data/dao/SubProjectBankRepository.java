package es.miw.tfm.invierte.core.infrastructure.data.dao;

import es.miw.tfm.invierte.core.infrastructure.data.entity.SubProjectBankEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing SubProjectBankEntity objects.
 * <p>
 * Extends JpaRepository to provide CRUD operations and custom query
 * methods for SubProjectBankEntity.
 * </p>
 *
 * @author denilssonmn
 * @see SubProjectBankEntity
 */
public interface SubProjectBankRepository extends JpaRepository<SubProjectBankEntity, Integer> {

}
