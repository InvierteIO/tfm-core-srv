package es.miw.tfm.invierte.core.infrastructure.data.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;

import es.miw.tfm.invierte.core.domain.model.PropertyGroupDocument;
import org.junit.jupiter.api.Test;

class PropertyGroupDocumentEntityTest {

  @Test
  void shouldBuildAndGetFields() {

    PropertyGroupDocumentEntity entity = PropertyGroupDocumentEntity.builder()
        .id(1)
        .filename("groupdoc.pdf")
        .path("/docs/groupdoc.pdf")
        .creationDate(LocalDateTime.of(2024, 6, 1, 10, 0))
        .description("desc")
        .build();

    assertEquals(1, entity.getId());
    assertEquals("groupdoc.pdf", entity.getFilename());
    assertEquals("/docs/groupdoc.pdf", entity.getPath());
    assertEquals(LocalDateTime.of(2024, 6, 1, 10, 0), entity.getCreationDate());
    assertEquals("desc", entity.getDescription());
  }

  @Test
  void shouldSetAndGetFields() {
    PropertyGroupDocumentEntity entity = new PropertyGroupDocumentEntity();
    entity.setId(2);
    entity.setFilename("doc.txt");
    entity.setPath("/docs/doc.txt");
    entity.setCreationDate(LocalDateTime.of(2024, 6, 2, 12, 0));
    entity.setDescription("desc2");

    assertEquals(2, entity.getId());
    assertEquals("doc.txt", entity.getFilename());
    assertEquals("/docs/doc.txt", entity.getPath());
    assertEquals(LocalDateTime.of(2024, 6, 2, 12, 0), entity.getCreationDate());
    assertEquals("desc2", entity.getDescription());
  }

  @Test
  void shouldConvertToPropertyGroupDocumentWithAllFields() {
    PropertyGroupDocumentEntity entity = new PropertyGroupDocumentEntity();
    entity.setId(4);
    entity.setFilename("test.pdf");
    entity.setPath("/t/test.pdf");
    entity.setCreationDate(LocalDateTime.of(2024, 6, 3, 14, 0));
    entity.setDescription("test desc");

    PropertyGroupDocument doc = entity.toPropertyGroupDocument();

    assertNotNull(doc);
    assertEquals(entity.getId(), doc.getId());
    assertEquals(entity.getFilename(), doc.getName());
    assertEquals(entity.getPath(), doc.getPath());
    assertEquals(entity.getDescription(), doc.getDescription());
    assertNotNull(doc.getCreatedAt());
  }

  @Test
  void shouldConvertToPropertyGroupDocumentWithNullFields() {
    PropertyGroupDocumentEntity entity = new PropertyGroupDocumentEntity();
    PropertyGroupDocument doc = entity.toPropertyGroupDocument();

    assertNotNull(doc);
    assertNull(doc.getId());
    assertNull(doc.getName());
    assertNull(doc.getPath());
    assertNull(doc.getDescription());
    assertNull(doc.getCreatedAt());
  }
}