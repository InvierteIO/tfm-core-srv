package es.miw.tfm.invierte.core.infrastructure.data.dao;

import es.miw.tfm.invierte.core.infrastructure.data.entity.MembershipEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing MembershipEntity objects.
 * This interface extends JpaRepository to provide CRUD operations and custom query methods
 * for the MembershipEntity.
 *
 * <p>The repository is responsible for interacting with the database to perform operations
 * related to membership levels.
 *
 * @author denilssonmn
 * @see MembershipEntity
 */
public interface MembershipRepository extends JpaRepository<MembershipEntity, Integer> {
  MembershipEntity findByLevel(String level);
}
