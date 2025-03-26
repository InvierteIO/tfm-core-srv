package es.miw.tfm.invierte.core.infrastructure.api.resource;

import es.miw.tfm.invierte.core.BaseContainerIntegrationTest;
import es.miw.tfm.invierte.core.domain.model.RealStateCompany;
import es.miw.tfm.invierte.core.infrastructure.data.dao.RealEstateCompanyRepository;
import es.miw.tfm.invierte.core.infrastructure.data.entity.RealEstateCompanyEntity;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

@ApiTestConfig
class RealStateCompanyResourceIT extends BaseContainerIntegrationTest {

  private static final String TAX_IDENTIFIER_NUMBER = "123456789";

  @Autowired
  private WebTestClient webTestClient;

  @Autowired
  private RealEstateCompanyRepository realEstateCompanyRepository;

  @BeforeAll
  public static void setup() {
    postgreSQLContainer.start();
  }

  @AfterAll
  public static void cleanup() {
    postgreSQLContainer.stop();
  }

  @Test
  void create_whenReceivedData_thenReturnOK() {
    this.deleteRealStateCompanies();
    RealStateCompany realStateCompany = RealStateCompany.builder()
        .name("Company New")
        .taxIdentificationNumber(TAX_IDENTIFIER_NUMBER)
        .build();

    this.webTestClient.post().uri(RealStateCompanyResource.REAL_STATE_COMPANIES)
        .bodyValue(realStateCompany)
        .exchange()
        .expectStatus().isOk()
        .expectBody(RealStateCompany.class)
        .value(response -> {
          Assertions.assertNotNull(response);
          Assertions.assertEquals(realStateCompany.getName(), response.getName());
          Assertions.assertEquals(TAX_IDENTIFIER_NUMBER, response.getTaxIdentificationNumber());
        });
  }

  @Test
  void update_whenReceivedData_thenReturnOK() {
    this.deleteRealStateCompanies();
    this.createRealStateCompany();

    RealStateCompany realStateCompany = RealStateCompany.builder()
        .name("Company Updated")
        .taxIdentificationNumber(TAX_IDENTIFIER_NUMBER)
        .build();

    this.webTestClient.put().uri(RealStateCompanyResource.REAL_STATE_COMPANIES + RealStateCompanyResource.REAL_STATE_TAX_IDENTIFIER,
            TAX_IDENTIFIER_NUMBER)
        .bodyValue(realStateCompany)
        .exchange()
        .expectStatus().isOk()
        .expectBody(RealStateCompany.class)
        .value(response -> {
          Assertions.assertNotNull(response);
          Assertions.assertEquals(realStateCompany.getName(), response.getName());
          Assertions.assertEquals(TAX_IDENTIFIER_NUMBER, response.getTaxIdentificationNumber());
        });
  }

  @Test
  void read_whenReceivedData_thenReturnOK() {
    this.deleteRealStateCompanies();
    this.createRealStateCompany();

    this.webTestClient.get().uri(RealStateCompanyResource.REAL_STATE_COMPANIES + RealStateCompanyResource.REAL_STATE_TAX_IDENTIFIER,
            TAX_IDENTIFIER_NUMBER)
        .exchange()
        .expectStatus().isOk()
        .expectBody(RealStateCompany.class)
        .value(response -> {
          Assertions.assertNotNull(response);
          Assertions.assertEquals(TAX_IDENTIFIER_NUMBER, response.getTaxIdentificationNumber());
        });
  }

  private void createRealStateCompany() {
    this.realEstateCompanyRepository.save(RealEstateCompanyEntity
        .builder()
        .name("Company Default")
        .taxIdentificationNumber(TAX_IDENTIFIER_NUMBER)
        .build());
  }

  private void deleteRealStateCompanies() {
    this.realEstateCompanyRepository.deleteAll();
  }
}
