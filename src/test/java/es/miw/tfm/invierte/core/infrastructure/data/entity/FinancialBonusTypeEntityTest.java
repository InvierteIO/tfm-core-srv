package es.miw.tfm.invierte.core.infrastructure.data.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import es.miw.tfm.invierte.core.domain.model.FinancialBonusType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class FinancialBonusTypeEntityTest {

  @Test
  void shouldBuildAndGetFields() {
    FinancialBonusEntity bonusEntity = FinancialBonusEntity.builder()
        .id(1)
        .code("FB01")
        .name("Bonus Name")
        .build();

    SubProjectBonusTypeEntity subProjectBonusType = new SubProjectBonusTypeEntity();
    FinancialBonusTypeEntity entity = FinancialBonusTypeEntity.builder()
        .id(10)
        .code("T01")
        .name("Type Name")
        .dataType("String")
        .required(true)
        .financialBonus(bonusEntity)
        .subProjectBonusTypes(List.of(subProjectBonusType))
        .build();

    assertEquals(10, entity.getId());
    assertEquals("T01", entity.getCode());
    assertEquals("Type Name", entity.getName());
    assertEquals("String", entity.getDataType());
    assertTrue(entity.getRequired());
    assertEquals(bonusEntity, entity.getFinancialBonus());
    assertNotNull(entity.getSubProjectBonusTypes());
    assertEquals(1, entity.getSubProjectBonusTypes().size());
  }

  @Test
  void shouldConvertToFinancialBonusTypeWithAllFields() {
    FinancialBonusEntity bonusEntity = Mockito.mock(FinancialBonusEntity.class);
    SubProjectBonusTypeEntity subProjectBonusType = Mockito.mock(SubProjectBonusTypeEntity.class);

    FinancialBonusTypeEntity entity = new FinancialBonusTypeEntity();
    entity.setId(20);
    entity.setCode("T02");
    entity.setName("Type 2");
    entity.setDataType("Integer");
    entity.setRequired(false);
    entity.setFinancialBonus(bonusEntity);
    entity.setSubProjectBonusTypes(List.of(subProjectBonusType));

    FinancialBonusType type = entity.toFinancialBonusType();

    assertNotNull(type);
    assertEquals(entity.getId(), type.getId());
    assertEquals(entity.getName(), type.getName());
    assertEquals(entity.getDataType(), type.getDataType());
    assertEquals(entity.getRequired(), type.getRequired());
  }

  @Test
  void shouldConvertToFinancialBonusTypeWithNullCollections() {
    FinancialBonusTypeEntity entity = new FinancialBonusTypeEntity();
    entity.setId(30);
    entity.setCode("T03");
    entity.setName("Type 3");
    entity.setDataType("Boolean");
    entity.setRequired(null);
    entity.setFinancialBonus(null);
    entity.setSubProjectBonusTypes(null);

    FinancialBonusType type = entity.toFinancialBonusType();

    assertNotNull(type);
    assertEquals(entity.getId(), type.getId());
    assertEquals(entity.getName(), type.getName());
    assertEquals(entity.getDataType(), type.getDataType());
    assertNull(type.getRequired());
  }

  @Test
  void shouldConvertToFinancialBonusTypeWithEmptySubProjectBonusTypes() {
    FinancialBonusTypeEntity entity = new FinancialBonusTypeEntity();
    entity.setId(40);
    entity.setCode("T04");
    entity.setName("Type 4");
    entity.setDataType("Double");
    entity.setRequired(true);
    entity.setFinancialBonus(null);
    entity.setSubProjectBonusTypes(List.of());

    FinancialBonusType type = entity.toFinancialBonusType();

    assertNotNull(type);
    assertEquals(entity.getId(), type.getId());
    assertEquals(entity.getName(), type.getName());
    assertEquals(entity.getDataType(), type.getDataType());
    assertTrue(type.getRequired());
  }
}