package es.miw.tfm.invierte.core.infrastructure.data.entity;

import es.miw.tfm.invierte.core.domain.model.SubProjectPropertyGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class SubProjectPropertyGroupEntityTest {

  private SubProjectEntity subProjectEntity;
  private PropertyGroupEntity propertyGroupEntity;
  private SubProjectPropertyGroupEntity entity;

  @BeforeEach
  void setUp() {
    // Mocked subproject
    subProjectEntity = new SubProjectEntity();
    subProjectEntity.setId(1);
    subProjectEntity.setName("Stage Alpha");

    // Mocked property group
    propertyGroupEntity = new PropertyGroupEntity();
    propertyGroupEntity.setId(10);
    propertyGroupEntity.setName("Group 1");

    // Entity under test
    entity = new SubProjectPropertyGroupEntity(subProjectEntity, propertyGroupEntity);
  }

  @Test
  void testConstructorInitializesFieldsCorrectly() {
    assertNotNull(entity.getCreationDate());
    assertEquals(subProjectEntity, entity.getSubProject());
    assertEquals(propertyGroupEntity, entity.getPropertyGroup());
    assertTrue(entity.getPropertyEntities().isEmpty());
    assertTrue(entity.getPropertyGroupDocumentEntities().isEmpty());
  }

  @Test
  void testToSubProjectPropertyGroup() {
    // Setup mock relationships
    PropertyEntity property = new PropertyEntity();
    property.setId(101);
    property.setSubProjectPropertyGroup(entity);

    PropertyGroupDocumentEntity doc = new PropertyGroupDocumentEntity();
    doc.setId(201);
    doc.setSubProjectPropertyGroup(entity);

    entity.setPropertyEntities(Collections.singletonList(property));
    entity.setPropertyGroupDocumentEntities(Collections.singletonList(doc));

    SubProjectPropertyGroup domain = entity.toSubProjectPropertyGroup();

    assertNotNull(domain);
    assertEquals(1, domain.getProperties().size());
    assertEquals(1, domain.getPropertyGroupDocuments().size());
    assertNotNull(domain.getStage());
    assertNotNull(domain.getPropertyGroup());
  }

  @Test
  void testToSubProjectPropertyGroupHandlesNullsGracefully() {
    SubProjectPropertyGroupEntity emptyEntity = new SubProjectPropertyGroupEntity();
    SubProjectPropertyGroup domain = emptyEntity.toSubProjectPropertyGroup();

    assertNotNull(domain);
    assertNotNull(domain.getProperties());
    assertNotNull(domain.getPropertyGroupDocuments());
    assertTrue(domain.getProperties().isEmpty());
    assertTrue(domain.getPropertyGroupDocuments().isEmpty());
    assertNull(domain.getStage());
    assertNull(domain.getPropertyGroup());
  }
}

