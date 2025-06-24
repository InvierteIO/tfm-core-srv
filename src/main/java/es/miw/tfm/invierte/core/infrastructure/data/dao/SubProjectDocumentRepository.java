package es.miw.tfm.invierte.core.infrastructure.data.dao;

import es.miw.tfm.invierte.core.infrastructure.data.entity.SubProjectDocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing SubProjectDocumentEntity objects.
 * <p>
 * Extends JpaRepository to provide CRUD operations and custom query
 * methods for SubProjectDocumentEntity.
 * </p>
 *
 * @author denilssonmn
 * @see SubProjectDocumentEntity
 */
public interface SubProjectDocumentRepository
    extends JpaRepository<SubProjectDocumentEntity, Integer> {

}
