package es.miw.tfm.invierte.core.infrastructure.data.dao;

import es.miw.tfm.invierte.core.infrastructure.data.entity.ProjectDocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing ProjectDocumentEntity objects.
 * <p>
 * Extends JpaRepository to provide CRUD operations and custom query
 * methods for ProjectDocumentEntity.
 * </p>
 *
 * @author denilssonmn
 * @see ProjectDocumentEntity
 */
public interface ProjectDocumentRepository extends JpaRepository<ProjectDocumentEntity, Integer> {

}
