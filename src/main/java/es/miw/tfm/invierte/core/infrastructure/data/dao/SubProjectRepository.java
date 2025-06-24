package es.miw.tfm.invierte.core.infrastructure.data.dao;

import es.miw.tfm.invierte.core.infrastructure.data.entity.SubProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing SubProjectEntity objects.
 * <p>
 * Extends JpaRepository to provide CRUD operations and custom query
 * methods for SubProjectEntity.
 * </p>
 *
 * @author denilssonmn
 * @see SubProjectEntity
 */
public interface SubProjectRepository extends JpaRepository<SubProjectEntity, Integer> {

}
