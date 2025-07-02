package es.miw.tfm.invierte.core.infrastructure.data.entity;

import es.miw.tfm.invierte.core.domain.model.Catalog;
import es.miw.tfm.invierte.core.domain.model.InfraInstallation;
import es.miw.tfm.invierte.core.domain.model.enums.InstallationType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InfrastructureInstallationEntityTest {

  @Test
  void shouldBuildAndGetFields() {
    CatalogEntity catalog = CatalogEntity.builder()
        .id(1)
        .code("CAT01")
        .name("Main Catalog")
        .description("Catalog description")
        .build();

    InfrastructureInstallationEntity entity = InfrastructureInstallationEntity.builder()
        .id(10)
        .code("INST01")
        .name("Installation Name")
        .dataType("String")
        .description("Description")
        .installationType(InstallationType.DEFINED)
        .other("Other info")
        .catalog(catalog)
        .subProjectInfrastructureInstallationEntities(List.of())
        .subProjectCatalogDetailEntities(List.of())
        .build();

    assertEquals(10, entity.getId());
    assertEquals("INST01", entity.getCode());
    assertEquals("Installation Name", entity.getName());
    assertEquals("String", entity.getDataType());
    assertEquals("Description", entity.getDescription());
    assertEquals(InstallationType.DEFINED, entity.getInstallationType());
    assertEquals("Other info", entity.getOther());
    assertEquals(catalog, entity.getCatalog());
    assertNotNull(entity.getSubProjectInfrastructureInstallationEntities());
    assertNotNull(entity.getSubProjectCatalogDetailEntities());
  }

  @Test
  void shouldConvertToInfraInstallationWithNullCatalog() {
    InfrastructureInstallationEntity entity = new InfrastructureInstallationEntity();
    entity.setId(2);
    entity.setCode("INST02");
    entity.setName("No Catalog");
    entity.setDataType("Integer");
    entity.setDescription("No catalog desc");
    entity.setInstallationType(InstallationType.DEFINED);
    entity.setOther("Other");
    entity.setCatalog(null);

    InfraInstallation infra = entity.toInfrastructureInstallation();

    assertNotNull(infra);
    assertEquals(entity.getId(), infra.getId());
    assertEquals(entity.getCode(), infra.getCode());
    assertEquals(entity.getName(), infra.getName());
    assertEquals(entity.getDataType(), infra.getDataType());
    assertEquals(entity.getDescription(), infra.getDescription());
    assertEquals(entity.getInstallationType(), infra.getInstallationType());
    assertEquals(entity.getOther(), infra.getOther());
    assertNull(infra.getCatalog());
  }

  @Test
  void shouldConvertToInfraInstallationWithCatalog() {
    CatalogEntity catalog = new CatalogEntity();
    catalog.setId(3);
    catalog.setCode("CAT03");
    catalog.setName("Catalog Name");
    catalog.setDescription("Catalog Desc");

    InfrastructureInstallationEntity entity = new InfrastructureInstallationEntity();
    entity.setId(4);
    entity.setCode("INST04");
    entity.setName("With Catalog");
    entity.setDataType("Double");
    entity.setDescription("With catalog desc");
    entity.setInstallationType(InstallationType.PROJECTED);
    entity.setOther("Other");
    entity.setCatalog(catalog);

    InfraInstallation infra = entity.toInfrastructureInstallation();

    assertNotNull(infra);
    assertEquals(entity.getId(), infra.getId());
    assertEquals(entity.getCode(), infra.getCode());
    assertEquals(entity.getName(), infra.getName());
    assertEquals(entity.getDataType(), infra.getDataType());
    assertEquals(entity.getDescription(), infra.getDescription());
    assertEquals(entity.getInstallationType(), infra.getInstallationType());
    assertEquals(entity.getOther(), infra.getOther());
    assertNotNull(infra.getCatalog());
    assertEquals(catalog.getId(), infra.getCatalog().getId());
    assertEquals(catalog.getCode(), infra.getCatalog().getCode());
    assertEquals(catalog.getName(), infra.getCatalog().getName());
    assertEquals(catalog.getDescription(), infra.getCatalog().getDescription());
  }

  @Test
  void shouldConvertToInfraInstallationWithMockedCatalog() {
    CatalogEntity catalog = Mockito.mock(CatalogEntity.class);
    Catalog catalogDomain = new Catalog();
    catalogDomain.setId(5);
    catalogDomain.setCode("CAT05");
    catalogDomain.setName("Mocked Catalog");
    catalogDomain.setDescription("Mocked Desc");
    Mockito.when(catalog.toCatalog()).thenReturn(catalogDomain);

    InfrastructureInstallationEntity entity = new InfrastructureInstallationEntity();
    entity.setId(6);
    entity.setCode("INST06");
    entity.setName("Mocked Catalog Entity");
    entity.setDataType("String");
    entity.setDescription("Desc");
    entity.setInstallationType(InstallationType.PROJECTED);
    entity.setOther("Other");
    entity.setCatalog(catalog);

    InfraInstallation infra = entity.toInfrastructureInstallation();

    assertNotNull(infra);
    assertNotNull(infra.getCatalog());
    assertEquals(5, infra.getCatalog().getId());
    assertEquals("CAT05", infra.getCatalog().getCode());
    assertEquals("Mocked Catalog", infra.getCatalog().getName());
    assertEquals("Mocked Desc", infra.getCatalog().getDescription());
  }

  @Test
  void shouldHandleNullAndEmptyCollections() {
    InfrastructureInstallationEntity entity = new InfrastructureInstallationEntity();
    entity.setId(7);
    entity.setCode("INST07");
    entity.setName("Null Collections");
    entity.setDataType("String");
    entity.setDescription("Desc");
    entity.setInstallationType(InstallationType.PROJECTED);
    entity.setOther("Other");
    entity.setSubProjectInfrastructureInstallationEntities(null);
    entity.setSubProjectCatalogDetailEntities(null);

    InfraInstallation infra = entity.toInfrastructureInstallation();

    assertNotNull(infra);
    assertEquals(entity.getId(), infra.getId());
    assertNull(entity.getSubProjectInfrastructureInstallationEntities());
    assertNull(entity.getSubProjectCatalogDetailEntities());
  }
}