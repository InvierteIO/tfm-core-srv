package es.miw.tfm.invierte.core.infrastructure.data.dao;

import es.miw.tfm.invierte.core.infrastructure.data.entity.PropertyDocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing PropertyDocumentEntity objects.
 * <p>
 * Extends JpaRepository to provide CRUD operations and custom query
 * methods for PropertyDocumentEntity.
 * </p>
 *
 * @author denilssonmn
 * @see PropertyDocumentEntity
 */
public interface PropertyDocumentRepository extends JpaRepository<PropertyDocumentEntity, Integer> {

}
