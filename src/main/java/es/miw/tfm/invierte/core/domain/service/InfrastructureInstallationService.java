package es.miw.tfm.invierte.core.domain.service;

import es.miw.tfm.invierte.core.domain.model.InfraInstallation;
import es.miw.tfm.invierte.core.domain.persistence.InfrastructureInstallationPersistence;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Service class for managing infrastructure installations. Delegates
 * persistence operations to the corresponding persistence interface and
 * provides reactive access to infrastructure installation domain objects.
 *
 * @author denilssonmn
 */
@Service
@RequiredArgsConstructor
public class InfrastructureInstallationService {

  private final InfrastructureInstallationPersistence infrastructureInstallationPersistence;

  public Flux<InfraInstallation> readAll() {
    return this.infrastructureInstallationPersistence.readAll();
  }

}
