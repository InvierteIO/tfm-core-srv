package es.miw.tfm.invierte.core.infrastructure.data.entity;

import es.miw.tfm.invierte.core.domain.model.PropertyFeature;
import es.miw.tfm.invierte.core.domain.model.enums.Flag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class PropertyGroupFeatureEntityTest {

  @Test
  void shouldBuildAndGetFields() {
    PropertyGroupEntity group = Mockito.mock(PropertyGroupEntity.class);
    FeatureEntity feature = Mockito.mock(FeatureEntity.class);

    PropertyGroupFeatureEntity entity = PropertyGroupFeatureEntity.builder()
        .id(1)
        .featureValue(Flag.YES)
        .propertyGroup(group)
        .feature(feature)
        .build();

    assertEquals(1, entity.getId());
    assertEquals(Flag.YES, entity.getFeatureValue());
    assertEquals(group, entity.getPropertyGroup());
    assertEquals(feature, entity.getFeature());
  }

  @Test
  void shouldSetAndGetFields() {
    PropertyGroupFeatureEntity entity = new PropertyGroupFeatureEntity();
    PropertyGroupEntity group = Mockito.mock(PropertyGroupEntity.class);
    FeatureEntity feature = Mockito.mock(FeatureEntity.class);

    entity.setId(2);
    entity.setFeatureValue(Flag.NO);
    entity.setPropertyGroup(group);
    entity.setFeature(feature);

    assertEquals(2, entity.getId());
    assertEquals(Flag.NO, entity.getFeatureValue());
    assertEquals(group, entity.getPropertyGroup());
    assertEquals(feature, entity.getFeature());
  }

  @Test
  void shouldConvertToPropertyFeatureWithAllFields() {
    FeatureEntity featureEntity = Mockito.mock(FeatureEntity.class);

    PropertyGroupFeatureEntity entity = PropertyGroupFeatureEntity.builder()
        .id(3)
        .featureValue(Flag.YES)
        .feature(featureEntity)
        .build();

    PropertyFeature propertyFeature = entity.toPropertyFeature();

    assertNotNull(propertyFeature);
    assertEquals(entity.getId(), propertyFeature.getId());
    assertEquals(entity.getFeatureValue(), propertyFeature.getFeatureValue());
  }

  @Test
  void shouldConvertToPropertyFeatureWithNullFeature() {
    PropertyGroupFeatureEntity entity = PropertyGroupFeatureEntity.builder()
        .id(4)
        .featureValue(Flag.NO)
        .feature(new FeatureEntity())
        .build();

    PropertyFeature propertyFeature = entity.toPropertyFeature();

    assertNotNull(propertyFeature);
    assertEquals(entity.getId(), propertyFeature.getId());
    assertEquals(entity.getFeatureValue(), propertyFeature.getFeatureValue());
    assertNotNull(propertyFeature.getFeature());
  }
}