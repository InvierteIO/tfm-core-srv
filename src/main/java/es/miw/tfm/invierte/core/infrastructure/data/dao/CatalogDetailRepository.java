package es.miw.tfm.invierte.core.infrastructure.data.dao;

import es.miw.tfm.invierte.core.infrastructure.data.entity.CatalogDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for managing CatalogDetailEntity objects.
 * Provides CRUD operations for catalog detail entities in the database.
 *
 * @author denilssonmn
 */
public interface CatalogDetailRepository extends JpaRepository<CatalogDetailEntity, Integer> {

}
