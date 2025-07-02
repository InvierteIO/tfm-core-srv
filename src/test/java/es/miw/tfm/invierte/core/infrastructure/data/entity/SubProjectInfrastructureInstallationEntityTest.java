package es.miw.tfm.invierte.core.infrastructure.data.entity;

import es.miw.tfm.invierte.core.domain.model.InfraInstallation;
import es.miw.tfm.invierte.core.domain.model.StageInfraInstallation;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;

import static org.junit.jupiter.api.Assertions.*;

class SubProjectInfrastructureInstallationEntityTest {

  @Test
  void shouldBuildAndGetFields() {
    SubProjectEntity subProject = Mockito.mock(SubProjectEntity.class);
    InfrastructureInstallationEntity infraEntity = Mockito.mock(InfrastructureInstallationEntity.class);

    SubProjectInfrastructureInstallationEntity entity = SubProjectInfrastructureInstallationEntity.builder()
        .id(1)
        .fieldValue("value")
        .subProject(subProject)
        .infrastructureInstallation(infraEntity)
        .build();

    assertEquals(1, entity.getId());
    assertEquals("value", entity.getFieldValue());
    assertEquals(subProject, entity.getSubProject());
    assertEquals(infraEntity, entity.getInfrastructureInstallation());
  }

  @Test
  void shouldSetAndGetFields() {
    SubProjectInfrastructureInstallationEntity entity = new SubProjectInfrastructureInstallationEntity();
    SubProjectEntity subProject = Mockito.mock(SubProjectEntity.class);
    InfrastructureInstallationEntity infraEntity = Mockito.mock(InfrastructureInstallationEntity.class);

    entity.setId(2);
    entity.setFieldValue("another");
    entity.setSubProject(subProject);
    entity.setInfrastructureInstallation(infraEntity);

    assertEquals(2, entity.getId());
    assertEquals("another", entity.getFieldValue());
    assertEquals(subProject, entity.getSubProject());
    assertEquals(infraEntity, entity.getInfrastructureInstallation());
  }

  @Test
  void shouldConstructFromDomain() {
    StageInfraInstallation stageInfra = new StageInfraInstallation();
    stageInfra.setFieldValue("domain-value");
    InfraInstallation infraInstallation = new InfraInstallation();
    infraInstallation.setName("InfraName");
    stageInfra.setInfraInstallation(infraInstallation);

    SubProjectEntity subProject = Mockito.mock(SubProjectEntity.class);

    SubProjectInfrastructureInstallationEntity entity =
        new SubProjectInfrastructureInstallationEntity(stageInfra, subProject);

    assertEquals("domain-value", entity.getFieldValue());
    assertEquals(subProject, entity.getSubProject());
    assertNotNull(entity.getInfrastructureInstallation());
    assertEquals("InfraName", entity.getInfrastructureInstallation().getName());
  }

  @Test
  void shouldConvertToDomain() {
    InfrastructureInstallationEntity infraEntity = Mockito.mock(InfrastructureInstallationEntity.class);
    StageInfraInstallation expectedDomain = new StageInfraInstallation();
    Mockito.when(infraEntity.toInfrastructureInstallation()).thenReturn(new InfraInstallation());

    SubProjectInfrastructureInstallationEntity entity = SubProjectInfrastructureInstallationEntity.builder()
        .id(5)
        .fieldValue("test")
        .infrastructureInstallation(infraEntity)
        .build();

    StageInfraInstallation domain = entity.toSubProjectInfrastructureInstallation();

    assertNotNull(domain);
    assertEquals("test", domain.getFieldValue());
    assertNotNull(domain.getInfraInstallation());
  }

}