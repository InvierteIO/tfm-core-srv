package es.miw.tfm.invierte.core.infrastructure.data.persistance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import es.miw.tfm.invierte.core.domain.model.RealStateCompany;
import es.miw.tfm.invierte.core.infrastructure.data.dao.RealEstateCompanyRepository;
import es.miw.tfm.invierte.core.infrastructure.data.entity.RealEstateCompanyEntity;
import es.miw.tfm.invierte.core.infrastructure.data.persistence.RealStateCompanyPersistenceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class RealStateCompanyPersistenceImplTest {

  @InjectMocks
  private RealStateCompanyPersistenceImpl realStateCompanyPersistence;

  @Mock
  private RealEstateCompanyRepository realEstateCompanyRepository;

  @Test
  void shouldCreateRealStateCompany() {
    RealStateCompany company = RealStateCompany.builder()
        .name("Company A")
        .taxIdentificationNumber("123456789")
        .build();
    RealEstateCompanyEntity entity = new RealEstateCompanyEntity(company);
    when(realEstateCompanyRepository.findByTaxIdentificationNumber("123456789")).thenReturn(null);
    when(realEstateCompanyRepository.save(any(RealEstateCompanyEntity.class))).thenReturn(entity);

    Mono<RealStateCompany> result = realStateCompanyPersistence.create(company);

    assertNotNull(result);
    assertEquals("Company A", result.block().getName());
    verify(realEstateCompanyRepository, times(1)).findByTaxIdentificationNumber("123456789");
    verify(realEstateCompanyRepository, times(1)).save(any(RealEstateCompanyEntity.class));
  }

  @Test
  void shouldReadRealStateCompanyByTaxIdentificationNumber() {
    String taxId = "123456789";
    RealEstateCompanyEntity entity = RealEstateCompanyEntity.builder()
        .name("Company A")
        .taxIdentificationNumber(taxId)
        .build();
    when(realEstateCompanyRepository.findByTaxIdentificationNumber(taxId)).thenReturn(entity);

    Mono<RealStateCompany> result = realStateCompanyPersistence.readByTaxIdentificationNumber(taxId);

    assertNotNull(result);
    assertEquals("Company A", result.block().getName());
    verify(realEstateCompanyRepository, times(1)).findByTaxIdentificationNumber(taxId);
  }

  @Test
  void shouldUpdateRealStateCompany() {
    String taxId = "123456789";
    RealStateCompany company = RealStateCompany.builder()
        .name("Updated Company")
        .taxIdentificationNumber(taxId)
        .build();
    RealEstateCompanyEntity existingEntity = RealEstateCompanyEntity.builder()
        .name("Old Company")
        .taxIdentificationNumber(taxId)
        .build();
    RealEstateCompanyEntity updatedEntity = new RealEstateCompanyEntity(company);

    when(realEstateCompanyRepository.findByTaxIdentificationNumber(taxId)).thenReturn(existingEntity);
    when(realEstateCompanyRepository.save(any(RealEstateCompanyEntity.class))).thenReturn(updatedEntity);

    Mono<RealStateCompany> result = realStateCompanyPersistence.update(taxId, company);

    assertNotNull(result);
    assertEquals(company.getName(), result.block().getName());
    verify(realEstateCompanyRepository, times(1)).findByTaxIdentificationNumber(taxId);
    verify(realEstateCompanyRepository, times(1)).save(any(RealEstateCompanyEntity.class));
  }

  @Test
  void shouldUpdateRealStateCompanyBasedOnParameter() {
    String taxId = "123456789";
    RealStateCompany company = RealStateCompany.builder()
        .name("Updated Company")
        .build();
    RealEstateCompanyEntity existingEntity = RealEstateCompanyEntity.builder()
        .name("Old Company")
        .taxIdentificationNumber(taxId)
        .build();
    RealEstateCompanyEntity updatedEntity = new RealEstateCompanyEntity(company);

    when(realEstateCompanyRepository.findByTaxIdentificationNumber(taxId)).thenReturn(existingEntity);
    when(realEstateCompanyRepository.save(argThat(realEstateCompanyEntity ->
        taxId.equals(realEstateCompanyEntity.getTaxIdentificationNumber()) &&
            company.getName().equals(realEstateCompanyEntity.getName()
        )))).thenReturn(updatedEntity);

    final var result  = realStateCompanyPersistence.update(taxId, company).block();

    assertNotNull(result);
    assertEquals(company.getName(), result.getName());
    verify(realEstateCompanyRepository, times(1)).findByTaxIdentificationNumber(taxId);
    verify(realEstateCompanyRepository, times(1)).save(argThat(realEstateCompanyEntity ->
        taxId.equals(realEstateCompanyEntity.getTaxIdentificationNumber()) &&
            company.getName().equals(realEstateCompanyEntity.getName()
        )));
  }


}
