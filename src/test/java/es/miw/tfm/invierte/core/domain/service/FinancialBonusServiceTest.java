package es.miw.tfm.invierte.core.domain.service;

import es.miw.tfm.invierte.core.domain.model.FinancialBonus;
import es.miw.tfm.invierte.core.domain.persistence.FinancialBonusPersistence;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FinancialBonusServiceTest {

  @Mock
  private FinancialBonusPersistence financialBonusPersistence;

  @InjectMocks
  private FinancialBonusService financialBonusService;

  @Test
  void readAll_shouldReturnAllFinancialBonuses() {
    FinancialBonus bonus1 = new FinancialBonus();
    FinancialBonus bonus2 = new FinancialBonus();
    when(financialBonusPersistence.readAll()).thenReturn(Flux.just(bonus1, bonus2));

    Flux<FinancialBonus> result = financialBonusService.readAll();

    StepVerifier.create(result)
        .expectNext(bonus1)
        .expectNext(bonus2)
        .verifyComplete();

    verify(financialBonusPersistence, times(1)).readAll();
  }
}