package es.miw.tfm.invierte.core.infrastructure.data.entity;

import es.miw.tfm.invierte.core.domain.model.Catalog;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CatalogEntityTest {

  @Test
  void shouldBuildAndGetFields() {
    CatalogEntity entity = CatalogEntity.builder()
        .id(1)
        .code("CAT01")
        .name("Main Catalog")
        .description("Catalog description")
        .build();

    assertEquals(1, entity.getId());
    assertEquals("CAT01", entity.getCode());
    assertEquals("Main Catalog", entity.getName());
    assertEquals("Catalog description", entity.getDescription());
  }

  @Test
  void shouldConvertToCatalog() {
    CatalogEntity entity = new CatalogEntity();
    entity.setId(2);
    entity.setCode("CAT02");
    entity.setName("Another Catalog");
    entity.setDescription("Another description");

    Catalog catalog = entity.toCatalog();

    assertNotNull(catalog);
    assertEquals(entity.getId(), catalog.getId());
    assertEquals(entity.getCode(), catalog.getCode());
    assertEquals(entity.getName(), catalog.getName());
    assertEquals(entity.getDescription(), catalog.getDescription());
  }

  @Test
  void shouldHandleNullFieldsInToCatalog() {
    CatalogEntity entity = new CatalogEntity();
    Catalog catalog = entity.toCatalog();

    assertNotNull(catalog);
    assertNull(catalog.getId());
    assertNull(catalog.getCode());
    assertNull(catalog.getName());
    assertNull(catalog.getDescription());
  }
}
