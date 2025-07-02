package es.miw.tfm.invierte.core.infrastructure.data.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import es.miw.tfm.invierte.core.domain.model.Bank;
import es.miw.tfm.invierte.core.domain.model.enums.FinancialEntityType;
import org.junit.jupiter.api.Test;

class BankEntityTest {

  @Test
  void shouldBuildAndGetFields() {
    SubProjectBankEntity subProjectBank = new SubProjectBankEntity();
    BankEntity entity = BankEntity.builder()
        .id(1)
        .name("Test Bank")
        .financialEntityType(FinancialEntityType.BANK)
        .subProjectBanks(List.of(subProjectBank))
        .build();

    assertEquals(1, entity.getId());
    assertEquals("Test Bank", entity.getName());
    assertEquals(FinancialEntityType.BANK, entity.getFinancialEntityType());
    assertNotNull(entity.getSubProjectBanks());
    assertEquals(1, entity.getSubProjectBanks().size());
  }

  @Test
  void shouldConvertToBank() {
    BankEntity entity = new BankEntity();
    entity.setId(2);
    entity.setName("Another Bank");
    entity.setFinancialEntityType(FinancialEntityType.BANK);

    Bank bank = entity.toBank();

    assertNotNull(bank);
    assertEquals(entity.getId(), bank.getId());
    assertEquals(entity.getName(), bank.getName());
  }

  @Test
  void shouldHandleNullFieldsInToBank() {
    BankEntity entity = new BankEntity();
    Bank bank = entity.toBank();

    assertNotNull(bank);
    assertNull(bank.getId());
    assertNull(bank.getName());
  }
}