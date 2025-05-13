package es.miw.tfm.invierte.core.infrastructure.data.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import es.miw.tfm.invierte.core.domain.model.RealStateCompany;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RealEstateCompanyEntityTest {

  @Test
  void shouldConvertToRealStateCompany() {
    RealEstateCompanyEntity entity = RealEstateCompanyEntity.builder()
        .id(1)
        .name("Real Estate Co.")
        .taxIdentificationNumber("123456789")
        .address("123 Main St")
        .phone("555-1234")
        .allowedMemberQuantity(50)
        .build();

    RealStateCompany realStateCompany = entity.toRealStateCompany();

    Assertions.assertNotNull(realStateCompany);
    assertEquals(entity.getName(), realStateCompany.getName());
    assertEquals(entity.getTaxIdentificationNumber(), realStateCompany.getTaxIdentificationNumber());
    assertEquals(entity.getAddress(), realStateCompany.getAddress());
    assertEquals(entity.getPhone(), realStateCompany.getPhone());
    assertEquals(entity.getAllowedMemberQuantity(), realStateCompany.getAllowedMemberQuantity());
  }

  @Test
  void shouldConvertFromRealStateCompany() {
    RealStateCompany realStateCompany = new RealStateCompany();
    realStateCompany.setName("Real Estate Co.");
    realStateCompany.setTaxIdentificationNumber("123456789");
    realStateCompany.setAddress("123 Main St");
    realStateCompany.setPhone("555-1234");
    realStateCompany.setAllowedMemberQuantity(50);

    RealEstateCompanyEntity entity = new RealEstateCompanyEntity(realStateCompany);

    Assertions.assertNotNull(entity);
    assertEquals(realStateCompany.getName(), entity.getName());
    assertEquals(realStateCompany.getTaxIdentificationNumber(), entity.getTaxIdentificationNumber());
    assertEquals(realStateCompany.getAddress(), entity.getAddress());
    assertEquals(realStateCompany.getPhone(), entity.getPhone());
    assertEquals(realStateCompany.getAllowedMemberQuantity(), entity.getAllowedMemberQuantity());
  }
}
