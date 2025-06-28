package es.miw.tfm.invierte.core.infrastructure.data.dao;

import es.miw.tfm.invierte.core.infrastructure.data.entity.CatalogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for managing CatalogEntity objects.
 * Provides CRUD operations for catalog entities in the database.
 *
 * @author denilssonmn
 */
public interface CatalogRepository extends JpaRepository<CatalogEntity, Integer> {

}
