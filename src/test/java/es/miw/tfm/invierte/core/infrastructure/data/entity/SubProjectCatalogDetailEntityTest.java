package es.miw.tfm.invierte.core.infrastructure.data.entity;

import es.miw.tfm.invierte.core.domain.model.CatalogDetail;
import es.miw.tfm.invierte.core.domain.model.InfraInstallation;
import es.miw.tfm.invierte.core.domain.model.StageCatalogDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubProjectCatalogDetailEntityTest {

  private StageCatalogDetail stageCatalogDetail;
  private SubProjectEntity subProjectEntity;

  @BeforeEach
  void setUp() {
    // Create domain CatalogDetail
    CatalogDetail catalogDetail = new CatalogDetail();
    catalogDetail.setId(1);
    catalogDetail.setDescription("Test catalog detail");

    // Create domain InfrastructureInstallation
    InfraInstallation infraInstallation = new InfraInstallation();
    infraInstallation.setId(2);
    infraInstallation.setName("Fiber Optic");

    // Populate StageCatalogDetail
    stageCatalogDetail = new StageCatalogDetail();
    stageCatalogDetail.setSituation("Active");
    stageCatalogDetail.setCatalogDetail(catalogDetail);
    stageCatalogDetail.setInfraInstallation(infraInstallation);

    // Mock SubProjectEntity
    subProjectEntity = new SubProjectEntity();
    subProjectEntity.setId(100);
    subProjectEntity.setName("Subproject A");
  }

  @Test
  void testConstructorFromStageCatalogDetail() {
    SubProjectCatalogDetailEntity entity =
        new SubProjectCatalogDetailEntity(stageCatalogDetail, subProjectEntity);

    assertEquals("Active", entity.getSituation());
    assertNotNull(entity.getSubProject());
    assertEquals(100, entity.getSubProject().getId());

    assertNotNull(entity.getCatalogDetail());
    assertEquals("Test catalog detail", entity.getCatalogDetail().getDescription());

    assertNotNull(entity.getInfrastructureInstallation());
    assertEquals("Fiber Optic", entity.getInfrastructureInstallation().getName());
  }

  @Test
  void testToSubProjectCatalogDetail() {
    SubProjectCatalogDetailEntity entity =
        new SubProjectCatalogDetailEntity(stageCatalogDetail, subProjectEntity);

    StageCatalogDetail domain = entity.toSubProjectCatalogDetail();

    assertEquals("Active", domain.getSituation());
    assertNotNull(domain.getCatalogDetail());
    assertEquals("Test catalog detail", domain.getCatalogDetail().getDescription());

    assertNotNull(domain.getInfraInstallation());
    assertEquals("Fiber Optic", domain.getInfraInstallation().getName());
  }
}
