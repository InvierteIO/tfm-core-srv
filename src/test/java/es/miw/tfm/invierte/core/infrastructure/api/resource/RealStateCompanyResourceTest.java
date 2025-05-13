package es.miw.tfm.invierte.core.infrastructure.api.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import es.miw.tfm.invierte.core.domain.model.RealStateCompany;
import es.miw.tfm.invierte.core.domain.model.dto.RealStateCompanyDto;
import es.miw.tfm.invierte.core.domain.service.RealStateCompanyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class RealStateCompanyResourceTest {

  @InjectMocks
  private RealStateCompanyResource realStateCompanyResource;

  @Mock
  private RealStateCompanyService realStateCompanyService;

  @Test
  void shouldCreateRealStateCompany() {
    RealStateCompany company = RealStateCompany.builder()
        .name("Company A")
        .taxIdentificationNumber("123456789")
        .build();
    when(realStateCompanyService.create(company)).thenReturn(Mono.just(company));

    Mono<RealStateCompany> response = realStateCompanyResource.create(company);

    assertNotNull(response);
    assertEquals("Company A", response.block().getName());
    verify(realStateCompanyService, times(1)).create(company);
  }

  @Test
  void shouldUpdateRealStateCompany() {
    RealStateCompany company = RealStateCompany.builder()
        .name("Updated Company")
        .taxIdentificationNumber("123456789")
        .build();
    when(realStateCompanyService.update("123456789", company)).thenReturn(Mono.just(company));

    Mono<RealStateCompany> response = realStateCompanyResource.update("123456789", company);

    assertNotNull(response);
    assertEquals("Updated Company", response.block().getName());
    verify(realStateCompanyService, times(1)).update("123456789", company);
  }

  @Test
  void shouldReadRealStateCompany() {
    RealStateCompany company = RealStateCompany.builder()
        .name("Company A")
        .taxIdentificationNumber("123456789")
        .build();
    when(realStateCompanyService.read("123456789")).thenReturn(Mono.just(company));

    Mono<RealStateCompany> response = realStateCompanyResource.read("123456789");

    assertNotNull(response);
    assertEquals("Company A", response.block().getName());
    verify(realStateCompanyService, times(1)).read("123456789");
  }

  @Test
  void shouldReadRealStateCompanyForProfile() {
    RealStateCompanyDto companyDto = RealStateCompanyDto.builder()
        .name("Company A")
        .taxIdentificationNumber("123456789")
        .build();
    when(realStateCompanyService.readForProfile("123456789")).thenReturn(Mono.just(companyDto));

    Mono<RealStateCompanyDto> response = realStateCompanyResource.readForProfile("123456789");

    assertNotNull(response);
    assertEquals("Company A", response.block().getName());
    verify(realStateCompanyService, times(1)).readForProfile("123456789");
  }
}
