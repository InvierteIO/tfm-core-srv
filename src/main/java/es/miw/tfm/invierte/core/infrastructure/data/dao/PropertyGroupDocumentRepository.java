package es.miw.tfm.invierte.core.infrastructure.data.dao;

import es.miw.tfm.invierte.core.infrastructure.data.entity.PropertyGroupDocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for managing {@link PropertyGroupDocumentEntity} entities.
 * Provides CRUD operations and query method support for property group documents.
 *
 * @author denilssonmn
 */
public interface PropertyGroupDocumentRepository extends
    JpaRepository<PropertyGroupDocumentEntity, Integer> {

}
