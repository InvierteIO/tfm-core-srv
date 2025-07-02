package es.miw.tfm.invierte.core.infrastructure.data.persistence;

import es.miw.tfm.invierte.core.domain.exception.NotFoundException;
import es.miw.tfm.invierte.core.domain.model.ProjectStage;
import es.miw.tfm.invierte.core.infrastructure.data.dao.SubProjectRepository;
import es.miw.tfm.invierte.core.infrastructure.data.entity.SubProjectEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class SubProjectPersistenceImplTest {

  private SubProjectRepository subProjectRepository;
  private SubProjectPersistenceImpl subProjectPersistence;

  @BeforeEach
  void setUp() {
    subProjectRepository = mock(SubProjectRepository.class);
    subProjectPersistence = new SubProjectPersistenceImpl(subProjectRepository);
  }

  @Test
  void readById_shouldReturnProjectStage_whenExists() {
    Integer id = 1;
    SubProjectEntity entity = mock(SubProjectEntity.class);
    ProjectStage expected = new ProjectStage();

    when(subProjectRepository.findById(id)).thenReturn(Optional.of(entity));
    when(entity.toProjectStage()).thenReturn(expected);

    Mono<ProjectStage> result = subProjectPersistence.readById(id);

    StepVerifier.create(result)
        .expectNext(expected)
        .verifyComplete();

    verify(subProjectRepository).findById(id);
    verify(entity).toProjectStage();
  }

}