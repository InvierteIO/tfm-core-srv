package es.miw.tfm.invierte.core.infrastructure.data.entity;

import es.miw.tfm.invierte.core.domain.model.Project;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProjectEntityTest {

  @Test
  void shouldBuildAndGetFields() {
    SubProjectEntity subProject = Mockito.mock(SubProjectEntity.class);
    ProjectEntity entity = ProjectEntity.builder()
        .id(1)
        .name("Project Name")
        .description("A test project")
        .subProjectEntities(List.of(subProject))
        .build();

    assertEquals(1, entity.getId());
    assertEquals("Project Name", entity.getName());
    assertEquals("A test project", entity.getDescription());
    assertNotNull(entity.getSubProjectEntities());
    assertEquals(1, entity.getSubProjectEntities().size());
  }

  @Test
  void shouldSetAndGetFields() {
    ProjectEntity entity = new ProjectEntity();
    entity.setId(2);
    entity.setName("Another Project");
    entity.setDescription("Desc");
    entity.setSubProjectEntities(null);

    assertEquals(2, entity.getId());
    assertEquals("Another Project", entity.getName());
    assertEquals("Desc", entity.getDescription());
    assertNull(entity.getSubProjectEntities());
  }

  @Test
  void shouldConstructFromDomainModel() {
    Project domain = new Project();
    domain.setId(3);
    domain.setName("Domain Project");
    domain.setDescription("Domain Desc");

    ProjectEntity entity = new ProjectEntity(domain);

    assertEquals(domain.getId(), entity.getId());
    assertEquals(domain.getName(), entity.getName());
    assertEquals(domain.getDescription(), entity.getDescription());
  }

  @Test
  void shouldConvertToProjectWithAllFields() {
    ProjectEntity entity = new ProjectEntity();
    entity.setId(4);
    entity.setName("Full Project");
    entity.setDescription("Full Desc");

    Project project = entity.toProject();

    assertNotNull(project);
    assertEquals(entity.getId(), project.getId());
    assertEquals(entity.getName(), project.getName());
    assertEquals(entity.getDescription(), project.getDescription());

  }

  @Test
  void shouldConvertToProjectWithNullFields() {
    ProjectEntity entity = new ProjectEntity();
    Project project = entity.toProject();

    assertNotNull(project);
    assertNull(project.getId());
    assertNull(project.getName());
    assertNull(project.getDescription());
  }
}