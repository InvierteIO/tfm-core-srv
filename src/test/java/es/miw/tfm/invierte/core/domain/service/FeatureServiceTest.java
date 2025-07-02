package es.miw.tfm.invierte.core.domain.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import es.miw.tfm.invierte.core.domain.model.Feature;
import es.miw.tfm.invierte.core.domain.persistence.FeaturePersistence;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class FeatureServiceTest {

  @Mock
  private FeaturePersistence featurePersistence;

  @InjectMocks
  private FeatureService featureService;

  @Test
  void readAll_shouldReturnAllFeatures() {
    Feature feature1 = new Feature();
    Feature feature2 = new Feature();
    when(featurePersistence.readAll()).thenReturn(Flux.just(feature1, feature2));

    Flux<Feature> result = featureService.readAll();

    StepVerifier.create(result)
        .expectNext(feature1)
        .expectNext(feature2)
        .verifyComplete();

    verify(featurePersistence, times(1)).readAll();
  }
}