package es.miw.tfm.invierte.core.infrastructure.api.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import es.miw.tfm.invierte.core.domain.model.LocationCode;
import es.miw.tfm.invierte.core.domain.service.LocationCodeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;

@ExtendWith(MockitoExtension.class)
class LocationCodeResourceTest {

  @Mock
  private LocationCodeService locationCodeService;

  @InjectMocks
  private LocationCodeResource locationCodeResource;

  @Test
  void shouldReadAllLocationCodes() {
    LocationCode code1 = LocationCode.builder().id(1).name("Code A").build();
    LocationCode code2 = LocationCode.builder().id(2).name("Code B").build();
    when(locationCodeService.readAll()).thenReturn(Flux.just(code1, code2));

    Flux<LocationCode> response = locationCodeResource.readAll();

    assertNotNull(response);
    assertEquals(2, response.collectList().block().size());
    verify(locationCodeService, times(1)).readAll();
  }
}