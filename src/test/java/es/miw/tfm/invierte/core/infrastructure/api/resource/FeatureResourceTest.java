package es.miw.tfm.invierte.core.infrastructure.api.resource;

import es.miw.tfm.invierte.core.domain.model.Feature;
import es.miw.tfm.invierte.core.domain.service.FeatureService;
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
class FeatureResourceTest {

  @Mock
  private FeatureService featureService;

  @InjectMocks
  private FeatureResource featureResource;

  @Test
  void shouldReadAllFeatures() {
    Feature feature1 = Feature.builder().id(1).name("Feature A").build();
    Feature feature2 = Feature.builder().id(2).name("Feature B").build();
    when(featureService.readAll()).thenReturn(Flux.just(feature1, feature2));

    Flux<Feature> response = featureResource.readAll();

    assertNotNull(response);
    assertEquals(2, response.collectList().block().size());
    verify(featureService, times(1)).readAll();
  }
}