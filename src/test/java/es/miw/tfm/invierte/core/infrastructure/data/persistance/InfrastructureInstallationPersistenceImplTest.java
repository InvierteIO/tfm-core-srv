package es.miw.tfm.invierte.core.infrastructure.data.persistence;

import es.miw.tfm.invierte.core.domain.model.InfraInstallation;
import es.miw.tfm.invierte.core.infrastructure.data.dao.InfrastructureInstallationRepository;
import es.miw.tfm.invierte.core.infrastructure.data.entity.InfrastructureInstallationEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class InfrastructureInstallationPersistenceImplTest {

  private InfrastructureInstallationRepository infrastructureInstallationRepository;
  private InfrastructureInstallationPersistenceImpl infrastructureInstallationPersistence;

  @BeforeEach
  void setUp() {
    infrastructureInstallationRepository = mock(InfrastructureInstallationRepository.class);
    infrastructureInstallationPersistence = new InfrastructureInstallationPersistenceImpl(infrastructureInstallationRepository);
  }

  @Test
  void readAll_shouldReturnAllInfrastructureInstallations() {
    InfrastructureInstallationEntity entity1 = mock(InfrastructureInstallationEntity.class);
    InfrastructureInstallationEntity entity2 = mock(InfrastructureInstallationEntity.class);
    InfraInstallation installation1 = new InfraInstallation();
    InfraInstallation installation2 = new InfraInstallation();

    when(entity1.toInfrastructureInstallation()).thenReturn(installation1);
    when(entity2.toInfrastructureInstallation()).thenReturn(installation2);

    List<InfrastructureInstallationEntity> entities = Arrays.asList(entity1, entity2);
    when(infrastructureInstallationRepository.findAll()).thenReturn(entities);

    Flux<InfraInstallation> result = infrastructureInstallationPersistence.readAll();

    StepVerifier.create(result)
        .expectNext(installation1)
        .expectNext(installation2)
        .verifyComplete();

    verify(infrastructureInstallationRepository, times(1)).findAll();
    verify(entity1, times(1)).toInfrastructureInstallation();
    verify(entity2, times(1)).toInfrastructureInstallation();
  }

  @Test
  void readAll_shouldReturnEmptyWhenNoInfrastructureInstallations() {
    when(infrastructureInstallationRepository.findAll()).thenReturn(List.of());

    Flux<InfraInstallation> result = infrastructureInstallationPersistence.readAll();

    StepVerifier.create(result)
        .verifyComplete();

    verify(infrastructureInstallationRepository, times(1)).findAll();
  }
}