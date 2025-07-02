package es.miw.tfm.invierte.core.infrastructure.api.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import es.miw.tfm.invierte.core.domain.model.Bank;
import es.miw.tfm.invierte.core.domain.service.BankService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;

@ExtendWith(MockitoExtension.class)
class BankResourceTest {

  @Mock
  private BankService bankService;

  @InjectMocks
  private BankResource bankResource;

  @Test
  void shouldReadAllBanks() {
    Bank bank1 = Bank.builder().id(1).name("Bank A").build();
    Bank bank2 = Bank.builder().id(2).name("Bank B").build();
    when(bankService.readAll()).thenReturn(Flux.just(bank1, bank2));

    Flux<Bank> response = bankResource.readAll();

    assertNotNull(response);
    assertEquals(2, response.collectList().block().size());
    verify(bankService, times(1)).readAll();
  }
}