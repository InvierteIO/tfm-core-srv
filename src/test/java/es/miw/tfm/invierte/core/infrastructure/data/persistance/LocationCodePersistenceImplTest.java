package es.miw.tfm.invierte.core.infrastructure.data.persistance;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import es.miw.tfm.invierte.core.domain.model.LocationCode;
import es.miw.tfm.invierte.core.infrastructure.data.dao.LocationCodeRepository;
import es.miw.tfm.invierte.core.infrastructure.data.entity.LocationCodeEntity;
import es.miw.tfm.invierte.core.infrastructure.data.persistence.LocationCodePersistenceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class LocationCodePersistenceImplTest {

  private LocationCodeRepository locationCodeRepository;
  private LocationCodePersistenceImpl locationCodePersistence;

  @BeforeEach
  void setUp() {
    locationCodeRepository = mock(LocationCodeRepository.class);
    locationCodePersistence = new LocationCodePersistenceImpl(locationCodeRepository);
  }

  @Test
  void readAll_shouldReturnAllLocationCodes() {
    LocationCodeEntity entity1 = mock(LocationCodeEntity.class);
    LocationCodeEntity entity2 = mock(LocationCodeEntity.class);
    LocationCode code1 = new LocationCode();
    LocationCode code2 = new LocationCode();

    when(entity1.toLocationCode()).thenReturn(code1);
    when(entity2.toLocationCode()).thenReturn(code2);

    List<LocationCodeEntity> entities = Arrays.asList(entity1, entity2);
    when(locationCodeRepository.findAll()).thenReturn(entities);

    Flux<LocationCode> result = locationCodePersistence.readAll();

    StepVerifier.create(result)
        .expectNext(code1)
        .expectNext(code2)
        .verifyComplete();

    verify(locationCodeRepository, times(1)).findAll();
    verify(entity1, times(1)).toLocationCode();
    verify(entity2, times(1)).toLocationCode();
  }

  @Test
  void readAll_shouldReturnEmptyWhenNoLocationCodes() {
    when(locationCodeRepository.findAll()).thenReturn(List.of());

    Flux<LocationCode> result = locationCodePersistence.readAll();

    StepVerifier.create(result)
        .verifyComplete();

    verify(locationCodeRepository, times(1)).findAll();
  }
}