package es.miw.tfm.invierte.core.infrastructure.data.entity;

import es.miw.tfm.invierte.core.domain.model.FinancialBonus;
import es.miw.tfm.invierte.core.domain.model.FinancialBonusType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FinancialBonusEntityTest {

  @Test
  void shouldBuildAndGetFields() {
    FinancialBonusEntity entity = FinancialBonusEntity.builder()
        .id(1)
        .code("FB01")
        .name("Bonus Name")
        .financialBonusTypeEntities(List.of())
        .build();

    assertEquals(1, entity.getId());
    assertEquals("FB01", entity.getCode());
    assertEquals("Bonus Name", entity.getName());
    assertNotNull(entity.getFinancialBonusTypeEntities());
    assertTrue(entity.getFinancialBonusTypeEntities().isEmpty());
  }

  @Test
  void shouldConvertToFinancialBonusWithNullTypes() {
    FinancialBonusEntity entity = new FinancialBonusEntity();
    entity.setId(2);
    entity.setCode("FB02");
    entity.setName("Null Types");
    entity.setFinancialBonusTypeEntities(null);

    FinancialBonus bonus = entity.toFinancialBonus();

    assertNotNull(bonus);
    assertEquals(entity.getId(), bonus.getId());
    assertEquals(entity.getName(), bonus.getName());
    assertNotNull(bonus.getTypes());
    assertTrue(bonus.getTypes().isEmpty());
  }

  @Test
  void shouldConvertToFinancialBonusWithEmptyTypes() {
    FinancialBonusEntity entity = new FinancialBonusEntity();
    entity.setId(3);
    entity.setCode("FB03");
    entity.setName("Empty Types");
    entity.setFinancialBonusTypeEntities(List.of());

    FinancialBonus bonus = entity.toFinancialBonus();

    assertNotNull(bonus);
    assertEquals(entity.getId(), bonus.getId());
    assertEquals(entity.getName(), bonus.getName());
    assertNotNull(bonus.getTypes());
    assertTrue(bonus.getTypes().isEmpty());
  }

  @Test
  void shouldConvertToFinancialBonusWithTypes() {
    FinancialBonusTypeEntity typeEntity = new FinancialBonusTypeEntity();
    typeEntity.setId(10);
    typeEntity.setCode("T01");
    typeEntity.setName("Type Name");

    FinancialBonusEntity entity = new FinancialBonusEntity();
    entity.setId(4);
    entity.setCode("FB04");
    entity.setName("With Types");
    entity.setFinancialBonusTypeEntities(List.of(typeEntity));

    FinancialBonus bonus = entity.toFinancialBonus();

    assertNotNull(bonus);
    assertEquals(entity.getId(), bonus.getId());
    assertEquals(entity.getName(), bonus.getName());
    assertNotNull(bonus.getTypes());
    assertEquals(1, bonus.getTypes().size());

    FinancialBonusType type = bonus.getTypes().get(0);
    assertEquals(typeEntity.getId(), type.getId());
    assertEquals(typeEntity.getName(), type.getName());
  }

  @Test
  void shouldConvertToFinancialBonusWithMockedTypeEntity() {
    FinancialBonusTypeEntity typeEntity = Mockito.mock(FinancialBonusTypeEntity.class);
    Mockito.when(typeEntity.getId()).thenReturn(20);
    Mockito.when(typeEntity.getName()).thenReturn("Mocked Type");

    FinancialBonusEntity entity = new FinancialBonusEntity();
    entity.setId(5);
    entity.setCode("FB05");
    entity.setName("Mocked Types");
    entity.setFinancialBonusTypeEntities(List.of(typeEntity));

    FinancialBonus bonus = entity.toFinancialBonus();

    assertNotNull(bonus);
    assertEquals(1, bonus.getTypes().size());
    FinancialBonusType type = bonus.getTypes().get(0);
    assertEquals(20, type.getId());
    assertEquals("Mocked Type", type.getName());
  }
}