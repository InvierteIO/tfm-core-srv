package es.miw.tfm.invierte.core.infrastructure.api.resource;

import es.miw.tfm.invierte.core.domain.model.FinancialBonus;
import es.miw.tfm.invierte.core.domain.service.FinancialBonusService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FinancialBonusResourceTest {

  @Mock
  private FinancialBonusService financialBonusService;

  @InjectMocks
  private FinancialBonusResource financialBonusResource;

  @Test
  void shouldReadAllFinancialBonuses() {
    FinancialBonus bonus1 = FinancialBonus.builder().id(1).name("Bonus A").build();
    FinancialBonus bonus2 = FinancialBonus.builder().id(2).name("Bonus B").build();
    when(financialBonusService.readAll()).thenReturn(Flux.just(bonus1, bonus2));

    Flux<FinancialBonus> response = financialBonusResource.readAll();

    assertNotNull(response);
    assertEquals(2, response.collectList().block().size());
    verify(financialBonusService, times(1)).readAll();
  }
}