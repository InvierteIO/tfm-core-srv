package es.miw.tfm.invierte.core.infrastructure.data.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

import es.miw.tfm.invierte.core.domain.model.CatalogDetail;
import es.miw.tfm.invierte.core.domain.model.ProjectDocument;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ProjectDocumentEntityTest {

  @Test
  void shouldBuildAndGetFields() {
    ProjectEntity project = Mockito.mock(ProjectEntity.class);
    CatalogDetailEntity catalogDetail = Mockito.mock(CatalogDetailEntity.class);

    ProjectDocumentEntity entity = ProjectDocumentEntity.builder()
        .id(1)
        .filename("file.pdf")
        .path("/docs/file.pdf")
        .creationDate(LocalDateTime.of(2024, 6, 1, 10, 0))
        .description("desc")
        .project(project)
        .catalogDetail(catalogDetail)
        .build();

    assertEquals(1, entity.getId());
    assertEquals("file.pdf", entity.getFilename());
    assertEquals("/docs/file.pdf", entity.getPath());
    assertEquals(LocalDateTime.of(2024, 6, 1, 10, 0), entity.getCreationDate());
    assertEquals("desc", entity.getDescription());
    assertEquals(project, entity.getProject());
    assertEquals(catalogDetail, entity.getCatalogDetail());
  }

  @Test
  void shouldSetAndGetFields() {
    ProjectDocumentEntity entity = new ProjectDocumentEntity();
    entity.setId(2);
    entity.setFilename("doc.txt");
    entity.setPath("/docs/doc.txt");
    entity.setCreationDate(LocalDateTime.of(2024, 6, 2, 12, 0));
    entity.setDescription("desc2");
    entity.setProject(null);
    entity.setCatalogDetail(null);

    assertEquals(2, entity.getId());
    assertEquals("doc.txt", entity.getFilename());
    assertEquals("/docs/doc.txt", entity.getPath());
    assertEquals(LocalDateTime.of(2024, 6, 2, 12, 0), entity.getCreationDate());
    assertEquals("desc2", entity.getDescription());
    assertNull(entity.getProject());
    assertNull(entity.getCatalogDetail());
  }

  @Test
  void shouldConstructFromDomainModel() {
    ProjectDocument domain = new ProjectDocument();
    domain.setId(3);
    domain.setName("domain.pdf");
    domain.setPath("/d/domain.pdf");
    domain.setDescription("domain desc");

    ProjectEntity project = Mockito.mock(ProjectEntity.class);
    CatalogDetailEntity catalogDetail = Mockito.mock(CatalogDetailEntity.class);

    ProjectDocumentEntity entity = new ProjectDocumentEntity(domain, project, catalogDetail);

    assertEquals(domain.getId(), entity.getId());
    assertEquals(domain.getPath(), entity.getPath());
    assertEquals(domain.getDescription(), entity.getDescription());
    assertNotNull(entity.getCreationDate());
    assertEquals(project, entity.getProject());
    assertEquals(catalogDetail, entity.getCatalogDetail());
  }

  @Test
  void shouldConvertToProjectDocumentWithAllFields() {
    CatalogDetailEntity catalogDetail = Mockito.mock(CatalogDetailEntity.class);
    CatalogDetail catalogDetailDomain = new CatalogDetail();
    Mockito.when(catalogDetail.toCatalogDetail()).thenReturn(catalogDetailDomain);

    ProjectDocumentEntity entity = new ProjectDocumentEntity();
    entity.setId(4);
    entity.setFilename("test.pdf");
    entity.setPath("/t/test.pdf");
    entity.setCreationDate(LocalDateTime.of(2024, 6, 3, 14, 0));
    entity.setDescription("test desc");
    entity.setCatalogDetail(catalogDetail);

    ProjectDocument doc = entity.toProjectDocument();

    assertNotNull(doc);
    assertEquals(entity.getId(), doc.getId());
    assertEquals(entity.getFilename(), doc.getName());
    assertEquals(entity.getPath(), doc.getPath());
    assertEquals(entity.getDescription(), doc.getDescription());
    assertEquals(LocalDate.of(2024, 6, 3), doc.getCreatedAt());
    assertEquals(catalogDetailDomain, doc.getCatalogDetail());
  }

  @Test
  void shouldConvertToProjectDocumentWithNullCreationDateAndCatalogDetail() {
    ProjectDocumentEntity entity = new ProjectDocumentEntity();
    entity.setId(5);
    entity.setFilename("null.pdf");
    entity.setPath("/n/null.pdf");
    entity.setDescription("null desc");
    entity.setCreationDate(null);
    entity.setCatalogDetail(null);

    ProjectDocument doc = entity.toProjectDocument();

    assertNotNull(doc);
    assertEquals(entity.getId(), doc.getId());
    assertEquals(entity.getFilename(), doc.getName());
    assertEquals(entity.getPath(), doc.getPath());
    assertEquals(entity.getDescription(), doc.getDescription());
    assertNull(doc.getCreatedAt());
    assertNull(doc.getCatalogDetail());
  }
}