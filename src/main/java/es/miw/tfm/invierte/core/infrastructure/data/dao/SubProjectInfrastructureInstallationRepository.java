package es.miw.tfm.invierte.core.infrastructure.data.dao;

import es.miw.tfm.invierte.core.infrastructure.data.entity.SubProjectInfrastructureInstallationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing SubProjectInfrastructureInstallationEntity objects.
 * <p>
 * Extends JpaRepository to provide CRUD operations and custom query
 * methods for SubProjectInfrastructureInstallationEntity.
 * </p>
 *
 * @author denilssonmn
 * @see SubProjectInfrastructureInstallationEntity
 */
public interface SubProjectInfrastructureInstallationRepository
    extends JpaRepository<SubProjectInfrastructureInstallationEntity, Integer> {

}
