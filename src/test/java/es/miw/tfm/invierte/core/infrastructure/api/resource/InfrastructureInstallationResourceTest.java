package es.miw.tfm.invierte.core.infrastructure.api.resource;

import es.miw.tfm.invierte.core.domain.model.InfraInstallation;
import es.miw.tfm.invierte.core.domain.service.InfrastructureInstallationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InfrastructureInstallationResourceTest {

  @Mock
  private InfrastructureInstallationService infrastructureInstallationService;

  @InjectMocks
  private InfrastructureInstallationResource infrastructureInstallationResource;

  @Test
  void shouldReadAllInfraInstallations() {
    InfraInstallation infra1 = InfraInstallation.builder().id(1).name("Infra A").build();
    InfraInstallation infra2 = InfraInstallation.builder().id(2).name("Infra B").build();
    when(infrastructureInstallationService.readAll()).thenReturn(Flux.just(infra1, infra2));

    Flux<InfraInstallation> response = infrastructureInstallationResource.readAll();

    assertNotNull(response);
    assertEquals(2, response.collectList().block().size());
    verify(infrastructureInstallationService, times(1)).readAll();
  }
}