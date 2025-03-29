package es.miw.tfm.invierte.core.domain.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import es.miw.tfm.invierte.core.domain.model.RealStateCompany;
import es.miw.tfm.invierte.core.domain.persistence.RealStateCompanyPersistence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class RealStateCompanyServiceTest {

  private static final String TAX_IDENTIFIER_NUMBER = "123456789";

  @InjectMocks
  private RealStateCompanyService realStateCompanyService;

  @Mock
  private RealStateCompanyPersistence realStateCompanyPersistence;

  @Test
  void create_whenRealStateReceived_thenReturnCreated() {
    final var mockedOutRealStateCompany = buildMockedOutRealStateCompany();
    final var mockedInRealStateCompany = buildMockedRealStateCompany();
    Mockito.when(this.realStateCompanyPersistence.create(Mockito.any())).thenReturn(mockedOutRealStateCompany);

    final var actualResult = this.realStateCompanyService.create(mockedInRealStateCompany);

    verify(this.realStateCompanyPersistence, times(1)).create(mockedInRealStateCompany);
    Assertions.assertNotNull(actualResult);
    final var realStateCompany = actualResult.block();

    Assertions.assertEquals(mockedInRealStateCompany, realStateCompany);
  }

  @Test
  void read_whenTaxIdentifierExists_thenReturnRealStateCompany() {

    final var mockedRealStateCompany = buildMockedOutRealStateCompany();
    Mockito.when(this.realStateCompanyPersistence.readByTaxIdentificationNumber(TAX_IDENTIFIER_NUMBER))
        .thenReturn(mockedRealStateCompany);

    final var actualResult = this.realStateCompanyService.read(TAX_IDENTIFIER_NUMBER);

    verify(this.realStateCompanyPersistence, times(1)).readByTaxIdentificationNumber(TAX_IDENTIFIER_NUMBER);
    Assertions.assertNotNull(actualResult);
    final var realStateCompany = actualResult.block();

    Assertions.assertEquals(mockedRealStateCompany.block(), realStateCompany);
  }

  @Test
  void update_whenTaxIdentifierExists_thenReturnUpdatedRealStateCompany() {

    final var mockedInRealStateCompany = buildMockedRealStateCompany();
    final var mockedOutRealStateCompany = buildMockedOutRealStateCompany();
    Mockito.when(this.realStateCompanyPersistence.update(TAX_IDENTIFIER_NUMBER, mockedInRealStateCompany))
        .thenReturn(mockedOutRealStateCompany);

    final var actualResult = this.realStateCompanyService.update(TAX_IDENTIFIER_NUMBER, mockedInRealStateCompany);

    verify(this.realStateCompanyPersistence, times(1)).update(TAX_IDENTIFIER_NUMBER, mockedInRealStateCompany);
    Assertions.assertNotNull(actualResult);
    final var realStateCompany = actualResult.block();

    Assertions.assertEquals(mockedOutRealStateCompany.block(), realStateCompany);
  }

  private Mono<RealStateCompany> buildMockedOutRealStateCompany() {
    return Mono.just(RealStateCompany.builder()
        .taxIdentificationNumber(TAX_IDENTIFIER_NUMBER)
        .name("Real State Company")
        .build());
  }

  private RealStateCompany buildMockedRealStateCompany() {
    return RealStateCompany.builder()
        .taxIdentificationNumber(TAX_IDENTIFIER_NUMBER)
        .name("Real State Company")
        .build();
  }

}
