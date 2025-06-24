package es.miw.tfm.invierte.core.infrastructure.data.dao;

import es.miw.tfm.invierte.core.infrastructure.data.entity.InfrastructureInstallationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing InfrastructureInstallationEntity objects.
 * <p>
 * Extends JpaRepository to provide CRUD operations and custom query
 * methods for InfrastructureInstallationEntity.
 * </p>
 *
 * @author denilssonmn
 * @see InfrastructureInstallationEntity
 */
public interface InfrastructureInstallationRepository
    extends JpaRepository<InfrastructureInstallationEntity, Integer> {

}
