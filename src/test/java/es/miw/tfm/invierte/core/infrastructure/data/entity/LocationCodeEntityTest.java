package es.miw.tfm.invierte.core.infrastructure.data.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import es.miw.tfm.invierte.core.domain.model.LocationCode;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class LocationCodeEntityTest {

  @Test
  void shouldBuildAndGetFields() {
    SubProjectEntity subProject = Mockito.mock(SubProjectEntity.class);

    LocationCodeEntity entity = LocationCodeEntity.builder()
        .id(1)
        .code("LOC001")
        .type("TYPE1")
        .name("Location Name")
        .subProjectEntities(List.of(subProject))
        .build();

    assertEquals(1, entity.getId());
    assertEquals("LOC001", entity.getCode());
    assertEquals("TYPE1", entity.getType());
    assertEquals("Location Name", entity.getName());
    assertNotNull(entity.getSubProjectEntities());
    assertEquals(1, entity.getSubProjectEntities().size());
  }

  @Test
  void shouldSetAndGetFields() {
    LocationCodeEntity entity = new LocationCodeEntity();
    entity.setId(2);
    entity.setCode("LOC002");
    entity.setType("TYPE2");
    entity.setName("Another Name");
    entity.setSubProjectEntities(null);

    assertEquals(2, entity.getId());
    assertEquals("LOC002", entity.getCode());
    assertEquals("TYPE2", entity.getType());
    assertEquals("Another Name", entity.getName());
    assertNull(entity.getSubProjectEntities());
  }

  @Test
  void shouldConvertToLocationCode() {
    LocationCodeEntity entity = new LocationCodeEntity();
    entity.setId(3);
    entity.setCode("LOC003");
    entity.setType("TYPE3");
    entity.setName("Third Name");

    LocationCode locationCode = entity.toLocationCode();

    assertNotNull(locationCode);
    assertEquals(entity.getId(), locationCode.getId());
    assertEquals(entity.getCode(), locationCode.getCode());
    assertEquals(entity.getType(), locationCode.getType());
    assertEquals(entity.getName(), locationCode.getName());
  }

  @Test
  void shouldHandleNullFieldsInToLocationCode() {
    LocationCodeEntity entity = new LocationCodeEntity();
    LocationCode locationCode = entity.toLocationCode();

    assertNotNull(locationCode);
    assertNull(locationCode.getId());
    assertNull(locationCode.getCode());
    assertNull(locationCode.getType());
    assertNull(locationCode.getName());
  }

  @Test
  void shouldConstructFromDomainModel() {
    LocationCode domain = new LocationCode();
    domain.setId(4);
    domain.setCode("LOC004");
    domain.setType("TYPE4");
    domain.setName("Domain Name");

    LocationCodeEntity entity = new LocationCodeEntity(domain);

    assertEquals(domain.getId(), entity.getId());
    assertEquals(domain.getCode(), entity.getCode());
    assertEquals(domain.getType(), entity.getType());
    assertEquals(domain.getName(), entity.getName());
  }
}