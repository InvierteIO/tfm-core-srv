package es.miw.tfm.invierte.core.infrastructure.data.dao;

import es.miw.tfm.invierte.core.infrastructure.data.entity.BankEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing BankEntity objects.
 * <p>
 * Extends JpaRepository to provide CRUD operations and custom query
 * methods for BankEntity.
 * </p>
 *
 * @author denilssonmn
 * @see BankEntity
 */
public interface BankRepository extends JpaRepository<BankEntity, Integer> {

}
