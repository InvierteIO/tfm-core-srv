package es.miw.tfm.invierte.core.infrastructure.data.entity;

import es.miw.tfm.invierte.core.domain.model.CatalogDetail;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CatalogDetailEntityTest {

  @Test
  void shouldBuildAndGetFields() {
    CatalogDetailEntity entity = CatalogDetailEntity.builder()
        .id(1)
        .code("CODE1")
        .name("Detail Name")
        .build();

    assertEquals(1, entity.getId());
    assertEquals("CODE1", entity.getCode());
    assertEquals("Detail Name", entity.getName());
  }

  @Test
  void shouldConvertToCatalogDetail() {
    CatalogDetailEntity entity = new CatalogDetailEntity();
    entity.setId(2);
    entity.setCode("CODE2");
    entity.setName("Another Detail");

    CatalogDetail detail = entity.toCatalogDetail();

    assertNotNull(detail);
    assertEquals(entity.getId(), detail.getId());
    assertEquals(entity.getCode(), detail.getCode());
    assertEquals(entity.getName(), detail.getName());
  }

  @Test
  void shouldHandleNullFieldsInToCatalogDetail() {
    CatalogDetailEntity entity = new CatalogDetailEntity();
    CatalogDetail detail = entity.toCatalogDetail();

    assertNotNull(detail);
    assertNull(detail.getId());
    assertNull(detail.getCode());
    assertNull(detail.getName());
  }
}