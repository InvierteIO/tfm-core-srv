package es.miw.tfm.invierte.core.infrastructure.data.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import es.miw.tfm.invierte.core.domain.model.Feature;
import org.junit.jupiter.api.Test;

class FeatureEntityTest {

  @Test
  void shouldBuildAndGetFields() {
    FeatureEntity entity = FeatureEntity.builder()
        .id(1)
        .code("FEAT01")
        .name("Feature Name")
        .build();

    assertEquals(1, entity.getId());
    assertEquals("FEAT01", entity.getCode());
    assertEquals("Feature Name", entity.getName());
  }

  @Test
  void shouldConvertToFeature() {
    FeatureEntity entity = new FeatureEntity();
    entity.setId(2);
    entity.setCode("FEAT02");
    entity.setName("Another Feature");

    Feature feature = entity.toFeature();

    assertNotNull(feature);
    assertEquals(entity.getId(), feature.getId());
    assertEquals(entity.getName(), feature.getName());
  }

  @Test
  void shouldHandleNullFieldsInToFeature() {
    FeatureEntity entity = new FeatureEntity();
    Feature feature = entity.toFeature();

    assertNotNull(feature);
    assertNull(feature.getId());
    assertNull(feature.getName());
  }
}