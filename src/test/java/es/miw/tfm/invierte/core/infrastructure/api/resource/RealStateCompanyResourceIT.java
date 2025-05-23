package es.miw.tfm.invierte.core.infrastructure.api.resource;

import es.miw.tfm.invierte.core.BaseContainerIntegration;
import es.miw.tfm.invierte.core.configuration.JwtService;
import es.miw.tfm.invierte.core.domain.model.RealStateCompany;
import es.miw.tfm.invierte.core.domain.model.dto.RealStateCompanyDto;
import es.miw.tfm.invierte.core.infrastructure.data.dao.RealEstateCompanyRepository;
import es.miw.tfm.invierte.core.infrastructure.data.entity.RealEstateCompanyEntity;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;

@ApiTestConfig
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Tag("IntegrationTest")
class RealStateCompanyResourceIT extends BaseContainerIntegration {

  private static final String TAX_IDENTIFICATION_NUMBER = "123456789";

  @Autowired
  private WebTestClient webTestClient;

  @Autowired
  private JwtService jwtService;

  @Autowired
  private RealEstateCompanyRepository realEstateCompanyRepository;

  @BeforeAll
  static void setup() {
    postgreSQLContainer.start();
  }

  @AfterAll
  static void cleanup() {
    postgreSQLContainer.stop();
  }

  @Test
  void create_whenReceivedData_thenReturnOK() {

    this.deleteRealStateCompanies();
    RealStateCompany realStateCompany = RealStateCompany.builder()
        .name("Company New")
        .taxIdentificationNumber(TAX_IDENTIFICATION_NUMBER)
        .build();

    this.webTestClient.post().uri(RealStateCompanyResource.REAL_STATE_COMPANIES)
        .bodyValue(realStateCompany)
        .exchange()
        .expectStatus().isOk()
        .expectBody(RealStateCompany.class)
        .value(response -> {
          Assertions.assertNotNull(response);
          Assertions.assertEquals(realStateCompany.getName(), response.getName());
          Assertions.assertEquals(TAX_IDENTIFICATION_NUMBER, response.getTaxIdentificationNumber());
        });
  }

  @Test
  void update_whenReceivedData_thenReturnOK() {
    this.deleteRealStateCompanies();
    this.createRealStateCompany();

    String token = this.jwtService
        .createToken("test@test.com", "test", "OWNER", TAX_IDENTIFICATION_NUMBER);
    String bearer = "Bearer " + token;
    RealStateCompany realStateCompany = RealStateCompany.builder()
        .name("Company Updated")
        .taxIdentificationNumber(TAX_IDENTIFICATION_NUMBER)
        .build();

    this.webTestClient.put().uri(RealStateCompanyResource.REAL_STATE_COMPANIES + RealStateCompanyResource.REAL_STATE_TAX_IDENTIFICATION_NUMBER,
            TAX_IDENTIFICATION_NUMBER)
        .header("Authorization", bearer)
        .bodyValue(realStateCompany)
        .exchange()
        .expectStatus().isOk()
        .expectBody(RealStateCompany.class)
        .value(response -> {
          Assertions.assertNotNull(response);
          Assertions.assertEquals(realStateCompany.getName(), response.getName());
          Assertions.assertEquals(TAX_IDENTIFICATION_NUMBER, response.getTaxIdentificationNumber());
        });
  }

  @Test
  void read_whenReceivedData_thenReturnOK() {
    this.deleteRealStateCompanies();
    this.createRealStateCompany();

    String token = this.jwtService
        .createToken("test@test.com", "test", "OWNER", TAX_IDENTIFICATION_NUMBER);
    String bearer = "Bearer " + token;

    this.webTestClient.get().uri(RealStateCompanyResource.REAL_STATE_COMPANIES + RealStateCompanyResource.REAL_STATE_TAX_IDENTIFICATION_NUMBER,
            TAX_IDENTIFICATION_NUMBER)
        .header("Authorization", bearer)
        .exchange()
        .expectStatus().isOk()
        .expectBody(RealStateCompany.class)
        .value(response -> {
          Assertions.assertNotNull(response);
          Assertions.assertEquals(TAX_IDENTIFICATION_NUMBER, response.getTaxIdentificationNumber());
        });
  }

    @Test
    void read_whenReceivedDataForProfile_thenReturnOK() {
        this.deleteRealStateCompanies();
        this.createRealStateCompany();

        String token = this.jwtService
                .createToken("test@test.com", "test", "OWNER", TAX_IDENTIFICATION_NUMBER);
        String bearer = "Bearer " + token;

        this.webTestClient.get().uri(RealStateCompanyResource.REAL_STATE_COMPANIES
            + RealStateCompanyResource.REAL_STATE_TAX_IDENTIFICATION_NUMBER + RealStateCompanyResource.PROFILE,
            TAX_IDENTIFICATION_NUMBER)
            .header("Authorization", bearer)
            .exchange()
            .expectStatus().isOk()
            .expectBody(RealStateCompanyDto.class)
            .value(response -> {
                Assertions.assertNotNull(response);
                Assertions.assertEquals(TAX_IDENTIFICATION_NUMBER, response.getTaxIdentificationNumber());
            });
    }

  private void createRealStateCompany() {
    this.realEstateCompanyRepository.save(RealEstateCompanyEntity
        .builder()
        .name("Company Default")
        .taxIdentificationNumber(TAX_IDENTIFICATION_NUMBER)
        .build());
  }

  private void deleteRealStateCompanies() {
    this.realEstateCompanyRepository.deleteAll();
  }
}
