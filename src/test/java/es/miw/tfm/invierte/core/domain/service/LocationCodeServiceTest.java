package es.miw.tfm.invierte.core.domain.service;

import es.miw.tfm.invierte.core.domain.model.LocationCode;
import es.miw.tfm.invierte.core.domain.persistence.LocationCodePersistence;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocationCodeServiceTest {

  @Mock
  private LocationCodePersistence locationCodePersistence;

  @InjectMocks
  private LocationCodeService locationCodeService;

  @Test
  void readAll_shouldReturnAllLocationCodes() {
    LocationCode code1 = new LocationCode();
    LocationCode code2 = new LocationCode();
    when(locationCodePersistence.readAll()).thenReturn(Flux.just(code1, code2));

    Flux<LocationCode> result = locationCodeService.readAll();

    StepVerifier.create(result)
        .expectNext(code1)
        .expectNext(code2)
        .verifyComplete();

    verify(locationCodePersistence, times(1)).readAll();
  }
}