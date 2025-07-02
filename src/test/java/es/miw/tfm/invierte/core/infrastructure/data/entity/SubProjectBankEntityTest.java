package es.miw.tfm.invierte.core.infrastructure.data.entity;

import es.miw.tfm.invierte.core.domain.model.StageBank;
import es.miw.tfm.invierte.core.domain.model.Bank;
import es.miw.tfm.invierte.core.domain.model.enums.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubProjectBankEntityTest {

  private StageBank mockStageBank;
  private SubProjectEntity mockSubProject;

  @BeforeEach
  void setUp() {
    Bank mockDomainBank = new Bank();
    mockDomainBank.setId(42);
    mockDomainBank.setName("Test Bank");

    mockStageBank = new StageBank();
    mockStageBank.setAccountNumber("123456789012345678");
    mockStageBank.setInterbankAccountNumber("98765432101234567890");
    mockStageBank.setCurrency(Currency.PEN);
    mockStageBank.setBank(mockDomainBank);

    mockSubProject = new SubProjectEntity();
    mockSubProject.setId(1);
  }

  @Test
  void testConstructorFromStageBank() {
    SubProjectBankEntity entity = new SubProjectBankEntity(mockStageBank, mockSubProject);

    assertEquals("123456789012345678", entity.getAccountNumber());
    assertEquals("98765432101234567890", entity.getInterbankAccountNumber());
    assertEquals(Currency.PEN, entity.getCurrency());
    assertNotNull(entity.getSubProject());
    assertEquals(1, entity.getSubProject().getId());

    assertNotNull(entity.getBank());
    assertEquals("Test Bank", entity.getBank().getName());
  }

  @Test
  void testToStageBank() {
    SubProjectBankEntity entity = new SubProjectBankEntity(mockStageBank, mockSubProject);
    StageBank result = entity.toStageBank();

    assertEquals("123456789012345678", result.getAccountNumber());
    assertEquals("98765432101234567890", result.getInterbankAccountNumber());
    assertEquals(Currency.PEN, result.getCurrency());

    assertNotNull(result.getBank());
    assertEquals("Test Bank", result.getBank().getName());
  }
}
