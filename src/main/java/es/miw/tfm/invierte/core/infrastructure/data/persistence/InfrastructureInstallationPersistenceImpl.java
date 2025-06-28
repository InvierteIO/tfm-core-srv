package es.miw.tfm.invierte.core.infrastructure.data.persistence;

import es.miw.tfm.invierte.core.domain.model.InfraInstallation;
import es.miw.tfm.invierte.core.domain.persistence.InfrastructureInstallationPersistence;
import es.miw.tfm.invierte.core.infrastructure.data.dao.InfrastructureInstallationRepository;
import es.miw.tfm.invierte.core.infrastructure.data.entity.InfrastructureInstallationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Spring Data JPA persistence implementation for infrastructure installations.
 * Provides methods to read and map infrastructure installation entities from
 * the database to the domain model.
 *
 * @author denilssonmn
 */
@Repository
@RequiredArgsConstructor
public class InfrastructureInstallationPersistenceImpl
    implements InfrastructureInstallationPersistence {

  private final InfrastructureInstallationRepository infrastructureInstallationRepository;

  @Override
  public Flux<InfraInstallation> readAll() {
    return Flux.defer(() -> Flux.fromIterable(this.infrastructureInstallationRepository.findAll()))
        .map(InfrastructureInstallationEntity::toInfrastructureInstallation);
  }
}
