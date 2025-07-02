package es.miw.tfm.invierte.core.infrastructure.data.persistence;

import es.miw.tfm.invierte.core.domain.model.Feature;
import es.miw.tfm.invierte.core.infrastructure.data.dao.FeatureRepository;
import es.miw.tfm.invierte.core.infrastructure.data.entity.FeatureEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class FeaturePersistenceImplTest {

  private FeatureRepository featureRepository;
  private FeaturePersistenceImpl featurePersistence;

  @BeforeEach
  void setUp() {
    featureRepository = mock(FeatureRepository.class);
    featurePersistence = new FeaturePersistenceImpl(featureRepository);
  }

  @Test
  void readAll_shouldReturnAllFeatures() {
    FeatureEntity entity1 = mock(FeatureEntity.class);
    FeatureEntity entity2 = mock(FeatureEntity.class);
    Feature feature1 = new Feature();
    Feature feature2 = new Feature();

    when(entity1.toFeature()).thenReturn(feature1);
    when(entity2.toFeature()).thenReturn(feature2);

    List<FeatureEntity> entities = Arrays.asList(entity1, entity2);
    when(featureRepository.findAll()).thenReturn(entities);

    Flux<Feature> result = featurePersistence.readAll();

    StepVerifier.create(result)
        .expectNext(feature1)
        .expectNext(feature2)
        .verifyComplete();

    verify(featureRepository, times(1)).findAll();
    verify(entity1, times(1)).toFeature();
    verify(entity2, times(1)).toFeature();
  }

  @Test
  void readAll_shouldReturnEmptyWhenNoFeatures() {
    when(featureRepository.findAll()).thenReturn(List.of());

    Flux<Feature> result = featurePersistence.readAll();

    StepVerifier.create(result)
        .verifyComplete();

    verify(featureRepository, times(1)).findAll();
  }
}