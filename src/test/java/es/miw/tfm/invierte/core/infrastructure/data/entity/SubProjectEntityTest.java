package es.miw.tfm.invierte.core.infrastructure.data.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDate;
import java.util.List;

import es.miw.tfm.invierte.core.domain.model.Bank;
import es.miw.tfm.invierte.core.domain.model.CatalogDetail;
import es.miw.tfm.invierte.core.domain.model.FinancialBonusType;
import es.miw.tfm.invierte.core.domain.model.InfraInstallation;
import es.miw.tfm.invierte.core.domain.model.LocationCode;
import es.miw.tfm.invierte.core.domain.model.ProjectStage;
import es.miw.tfm.invierte.core.domain.model.StageBank;
import es.miw.tfm.invierte.core.domain.model.StageBonusType;
import es.miw.tfm.invierte.core.domain.model.StageCatalogDetail;
import es.miw.tfm.invierte.core.domain.model.StageInfraInstallation;
import es.miw.tfm.invierte.core.domain.model.enums.CommercializationCycle;
import es.miw.tfm.invierte.core.domain.model.enums.SubProjectStatus;
import org.junit.jupiter.api.Test;

class SubProjectEntityTest {

  @Test
  void testConstructorWithoutProjectEntity() {

    StageBank stageBank = new StageBank();
    stageBank.setAccountNumber("123456");
    stageBank.setInterbankAccountNumber("12345678901234567890");

    Bank bank = new Bank();
    bank.setName("Test Bank");
    stageBank.setBank(bank);

    StageBonusType stageBonusType = new StageBonusType();
    stageBonusType.setTypeValue("Bonus A");

    FinancialBonusType bonusType = new FinancialBonusType();
    bonusType.setName("Promo 1");
    stageBonusType.setFinancialBonusType(bonusType);

    StageCatalogDetail stageCatalogDetail = new StageCatalogDetail();
    CatalogDetail catalogDetail = new CatalogDetail();
    catalogDetail.setDescription("Catalog Detail Desc");
    stageCatalogDetail.setCatalogDetail(catalogDetail);

    InfraInstallation infraInstallation = new InfraInstallation();
    infraInstallation.setName("Fiber Optic");
    stageCatalogDetail.setInfraInstallation(infraInstallation);

    LocationCode locationCode = new LocationCode();
    locationCode.setCode("123456");

    // Domain model
    ProjectStage projectStage = new ProjectStage();
    projectStage.setName("SubProject A");
    projectStage.setStage("S1");
    projectStage.setStatus(SubProjectStatus.DRAFT);
    projectStage.setCommercializationCycle(CommercializationCycle.PRE_SALES);
    projectStage.setZipCode("00000");
    projectStage.setAddress("Test St.");
    projectStage.setAddressNumber("42");
    projectStage.setAddressReference("Near Park");
    projectStage.setKmlKmzUrl("http://maps.kml");

    projectStage.setStageBanks(List.of(stageBank));
    projectStage.setStageBonusTypes(List.of(stageBonusType));
    projectStage.setStageCatalogDetails(List.of(stageCatalogDetail));
    projectStage.setStageInfraInstallations(List.of(new StageInfraInstallation()));
    projectStage.setLocationCode(locationCode);
    projectStage.setEndDate(LocalDate.of(2025, 1, 1));
    projectStage.setHandOverDate(LocalDate.of(2025, 6, 1));

    ProjectEntity projectEntity = new ProjectEntity();
    projectEntity.setId(1);

    SubProjectEntity subProject = new SubProjectEntity(projectStage);

    assertEquals("SubProject A", subProject.getName());
    assertEquals("S1", subProject.getStage());
    assertEquals(SubProjectStatus.DRAFT, subProject.getStatus());
    assertEquals(1, subProject.getSubProjectBanks().size());
    assertEquals(1, subProject.getSubProjectBonusTypes().size());
    assertNull(subProject.getProject());
  }

  @Test
  void shouldConstructFromDomainModelAndProjectEntity() {
    Bank bank = new Bank();
    bank.setId(1);
    bank.setName("Test Bank");

    StageBank stageBank = new StageBank();
    stageBank.setAccountNumber("ACC123");
    stageBank.setBank(bank);

    FinancialBonusType financialBonusType = new FinancialBonusType();
    financialBonusType.setId(1);

    StageBonusType stageBonusType = new StageBonusType();
    stageBonusType.setTypeValue("BONUS");
    stageBonusType.setFinancialBonusType(financialBonusType);

    InfraInstallation infraInstallation = new InfraInstallation();
    infraInstallation.setId(1);

    StageInfraInstallation stageInfraInstallation = new StageInfraInstallation();
    stageInfraInstallation.setId(1);
    stageInfraInstallation.setFieldValue("EMPTY");
    stageInfraInstallation.setInfraInstallation(infraInstallation);

    StageCatalogDetail stageCatalogDetail = new StageCatalogDetail();
    CatalogDetail catalogDetail = new CatalogDetail();
    catalogDetail.setDescription("Detail");
    stageCatalogDetail.setCatalogDetail(catalogDetail);

    LocationCode locationCode = new LocationCode();
    locationCode.setCode("LOC123");

    ProjectStage projectStage = new ProjectStage();
    projectStage.setName("SubProject");
    projectStage.setStage("S1");
    projectStage.setKmlKmzUrl("http://kml");
    projectStage.setZipCode("12345");
    projectStage.setAddress("Main St");
    projectStage.setAddressReference("Near Park");
    projectStage.setAddressNumber("10A");
    projectStage.setStatus(SubProjectStatus.DRAFT);
    projectStage.setEndDate(LocalDate.of(2025, 1, 1));
    projectStage.setHandOverDate(LocalDate.of(2025, 6, 1));
    projectStage.setCommercializationCycle(CommercializationCycle.PRE_SALES);
    projectStage.setStageBanks(List.of(stageBank));
    projectStage.setStageBonusTypes(List.of(stageBonusType));
    projectStage.setStageInfraInstallations(List.of(stageInfraInstallation));
    projectStage.setStageCatalogDetails(List.of(stageCatalogDetail));
    projectStage.setLocationCode(locationCode);

    ProjectEntity projectEntity = new ProjectEntity();
    projectEntity.setId(99);


    SubProjectEntity entity = new SubProjectEntity(projectStage, projectEntity);

    assertEquals("SubProject", entity.getName());
    assertEquals("S1", entity.getStage());
    assertEquals("http://kml", entity.getKmlKmzUrl());
    assertEquals("12345", entity.getZipCode());
    assertEquals("Main St", entity.getAddress());
    assertEquals("Near Park", entity.getAddressReference());
    assertEquals("10A", entity.getAddressNumber());
    assertEquals(SubProjectStatus.DRAFT, entity.getStatus());
    assertEquals(LocalDate.of(2025, 1, 1), entity.getEndDate());
    assertEquals(LocalDate.of(2025, 6, 1), entity.getHandOverDate());
    assertEquals(CommercializationCycle.PRE_SALES, entity.getCommercializationCycle());
    assertSame(projectEntity, entity.getProject());

    assertNotNull(entity.getSubProjectBanks());
    assertEquals(1, entity.getSubProjectBanks().size());

    assertNotNull(entity.getSubProjectBonusTypes());
    assertEquals(1, entity.getSubProjectBonusTypes().size());

    assertNotNull(entity.getSubProjectInfrastructureInstallationEntities());
    assertEquals(1, entity.getSubProjectInfrastructureInstallationEntities().size());

    assertNotNull(entity.getSubProjectCatalogDetailEntities());
    assertEquals(1, entity.getSubProjectCatalogDetailEntities().size());

    assertNotNull(entity.getLocationCode());
    assertEquals("LOC123", entity.getLocationCode().getCode());
  }

  @Test
  void shouldConvertToProjectWithAllFields() {
    SubProjectEntity entity = new SubProjectEntity();
    entity.setId(1);
    entity.setName("SubProject");
    entity.setStage("S1");
    entity.setKmlKmzUrl("http://kml");
    entity.setZipCode("12345");
    entity.setAddress("Main St");
    entity.setAddressReference("Near Park");
    entity.setAddressNumber("10A");
    entity.setStatus(SubProjectStatus.DRAFT);
    entity.setEndDate(LocalDate.of(2025, 1, 1));
    entity.setHandOverDate(LocalDate.of(2025, 6, 1));
    entity.setCommercializationCycle(CommercializationCycle.PRE_SALES);

    ProjectEntity projectEntity = new ProjectEntity();
    projectEntity.setId(99);
    entity.setProject(projectEntity);

    BankEntity bank = new BankEntity();
    bank.setId(1);
    SubProjectBankEntity subProjectBankEntity = new SubProjectBankEntity();
    subProjectBankEntity.setId(1);
    subProjectBankEntity.setBank(bank);
    entity.getSubProjectBanks().add(subProjectBankEntity);

    FinancialBonusTypeEntity financialBonusTypeEntity = new FinancialBonusTypeEntity();
    financialBonusTypeEntity.setId(1);
    SubProjectBonusTypeEntity subProjectBonusTypeEntity = new SubProjectBonusTypeEntity();
    subProjectBonusTypeEntity.setId(1);
    subProjectBonusTypeEntity.setFinancialBonusType(financialBonusTypeEntity);
    entity.getSubProjectBonusTypes().add(subProjectBonusTypeEntity);


    InfrastructureInstallationEntity infrastructureInstallation = new InfrastructureInstallationEntity();
    infrastructureInstallation.setId(1);
    SubProjectInfrastructureInstallationEntity subProjectInfrastructureInstallationEntity =
        new SubProjectInfrastructureInstallationEntity();
    subProjectInfrastructureInstallationEntity.setId(1);
    subProjectInfrastructureInstallationEntity.setInfrastructureInstallation(infrastructureInstallation);
    entity.getSubProjectInfrastructureInstallationEntities().add(subProjectInfrastructureInstallationEntity);

    SubProjectCatalogDetailEntity subProjectCatalogDetailEntity = new SubProjectCatalogDetailEntity();
    subProjectCatalogDetailEntity.setId(1);
    entity.getSubProjectCatalogDetailEntities().add(subProjectCatalogDetailEntity);

    LocationCodeEntity locationCodeEntity = new LocationCodeEntity();
    locationCodeEntity.setId(1);
    entity.setLocationCode(locationCodeEntity);

    ProjectStage projectStage = entity.toProjectStage();

    assertEquals("SubProject", projectStage.getName());
    assertEquals("S1", projectStage.getStage());
    assertEquals("http://kml", projectStage.getKmlKmzUrl());
    assertEquals("12345", projectStage.getZipCode());
    assertEquals("Main St", projectStage.getAddress());
    assertEquals("Near Park", projectStage.getAddressReference());
    assertEquals("10A", projectStage.getAddressNumber());
    assertEquals(SubProjectStatus.DRAFT, projectStage.getStatus());
    assertEquals(LocalDate.of(2025, 1, 1), projectStage.getEndDate());
    assertEquals(LocalDate.of(2025, 6, 1), projectStage.getHandOverDate());
    assertEquals(CommercializationCycle.PRE_SALES, projectStage.getCommercializationCycle());
    assertEquals(1, projectStage.getStageBanks().size());
    assertEquals(1, projectStage.getStageBonusTypes().size());
    assertEquals(1, projectStage.getStageInfraInstallations().size());
    assertEquals(1, projectStage.getStageCatalogDetails().size());
    assertNotNull(projectStage.getLocationCode());
  }


}
