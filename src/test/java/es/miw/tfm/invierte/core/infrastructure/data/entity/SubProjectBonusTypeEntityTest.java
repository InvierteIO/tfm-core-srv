package es.miw.tfm.invierte.core.infrastructure.data.entity;

import es.miw.tfm.invierte.core.domain.model.FinancialBonusType;
import es.miw.tfm.invierte.core.domain.model.StageBonusType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubProjectBonusTypeEntityTest {

  private StageBonusType mockStageBonusType;
  private SubProjectEntity mockSubProject;

  @BeforeEach
  void setUp() {
    FinancialBonusType domainFinancialBonusType = new FinancialBonusType();
    domainFinancialBonusType.setId(10);
    domainFinancialBonusType.setName("Government Incentive");

    mockStageBonusType = new StageBonusType();
    mockStageBonusType.setTypeValue("10% discount for early buyers");
    mockStageBonusType.setFinancialBonusType(domainFinancialBonusType);

    mockSubProject = new SubProjectEntity();
    mockSubProject.setId(100);
    mockSubProject.setName("SubProject A");
  }

  @Test
  void testConstructorFromStageBonusType() {
    SubProjectBonusTypeEntity entity = new SubProjectBonusTypeEntity(mockStageBonusType, mockSubProject);

    assertEquals("10% discount for early buyers", entity.getTypeValue());
    assertNotNull(entity.getSubProject());
    assertEquals(100, entity.getSubProject().getId());
    assertNotNull(entity.getFinancialBonusType());
    assertEquals("Government Incentive", entity.getFinancialBonusType().getName());
    assertEquals(10, entity.getFinancialBonusType().getId());
  }

  @Test
  void testToStageBonusTypes() {
    SubProjectBonusTypeEntity entity = new SubProjectBonusTypeEntity(mockStageBonusType, mockSubProject);
    StageBonusType domain = entity.toStageBonusTypes();

    assertEquals("10% discount for early buyers", domain.getTypeValue());
    assertNotNull(domain.getFinancialBonusType());
    assertEquals("Government Incentive", domain.getFinancialBonusType().getName());
    assertEquals(10, domain.getFinancialBonusType().getId());
  }
}
