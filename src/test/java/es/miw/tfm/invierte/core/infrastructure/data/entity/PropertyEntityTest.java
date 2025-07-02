package es.miw.tfm.invierte.core.infrastructure.data.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import es.miw.tfm.invierte.core.domain.model.Property;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PropertyEntityTest {

  @Test
  void shouldBuildAndGetFields() {
    PropertyEntity entity = PropertyEntity.builder()
        .id(1)
        .name("Property Name")
        .address("123 Main St")
        .price(100000.0)
        .build();

    assertEquals(1, entity.getId());
    assertEquals("Property Name", entity.getName());
    assertEquals("123 Main St", entity.getAddress());
    assertEquals(Double.valueOf(100000), entity.getPrice());

  }

  @Test
  void shouldSetAndGetFields() {
    PropertyEntity entity = new PropertyEntity();
    entity.setId(2);
    entity.setName("Another Property");
    entity.setAddress("456 Side St");
    entity.setPrice(200000.0);

    assertEquals(2, entity.getId());
    assertEquals("Another Property", entity.getName());
    assertEquals("456 Side St", entity.getAddress());
    assertEquals(Double.valueOf(200000), entity.getPrice());

  }

  @Test
  void shouldConstructFromDomainModel() {
    Property domain = new Property();
    domain.setId(3);
    domain.setName("Domain Property");
    domain.setAddress("789 Domain Ave");
    domain.setPrice(300000.0);

    PropertyEntity entity = new PropertyEntity(domain);

    assertEquals(domain.getId(), entity.getId());
    assertEquals(domain.getName(), entity.getName());
    assertEquals(domain.getAddress(), entity.getAddress());
    assertEquals(domain.getPrice(), entity.getPrice());
  }

  @Test
  void shouldConvertToPropertyWithAllFields() {
    PropertyEntity entity = new PropertyEntity();
    entity.setId(4);
    entity.setName("Full Property");
    entity.setAddress("1010 Full St");
    entity.setPrice(400000.0);

    Property property = entity.toProperty();

    assertNotNull(property);
    assertEquals(entity.getId(), property.getId());
    assertEquals(entity.getName(), property.getName());
    assertEquals(entity.getAddress(), property.getAddress());
    assertEquals(entity.getPrice(), property.getPrice());
  }

  @Test
  void shouldConvertToPropertyWithNullFields() {
    PropertyEntity entity = new PropertyEntity();
    Property property = entity.toProperty();

    assertNotNull(property);
    assertNull(property.getId());
    assertNull(property.getName());
    assertNull(property.getAddress());
    assertNull(property.getPrice());
  }
}