package es.miw.tfm.invierte.core.domain.service;

import es.miw.tfm.invierte.core.domain.model.InfraInstallation;
import es.miw.tfm.invierte.core.domain.persistence.InfrastructureInstallationPersistence;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InfrastructureInstallationServiceTest {

  @Mock
  private InfrastructureInstallationPersistence infrastructureInstallationPersistence;

  @InjectMocks
  private InfrastructureInstallationService infrastructureInstallationService;

  @Test
  void readAll_shouldReturnAllInfraInstallations() {
    InfraInstallation installation1 = new InfraInstallation();
    InfraInstallation installation2 = new InfraInstallation();
    when(infrastructureInstallationPersistence.readAll()).thenReturn(Flux.just(installation1, installation2));

    Flux<InfraInstallation> result = infrastructureInstallationService.readAll();

    StepVerifier.create(result)
        .expectNext(installation1)
        .expectNext(installation2)
        .verifyComplete();

    verify(infrastructureInstallationPersistence, times(1)).readAll();
  }
}