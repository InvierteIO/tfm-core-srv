package es.miw.tfm.invierte.core.domain.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import es.miw.tfm.invierte.core.domain.model.Bank;
import es.miw.tfm.invierte.core.domain.persistence.BankPersistence;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class BankServiceTest {

  @Mock
  private BankPersistence bankPersistence;

  @InjectMocks
  private BankService bankService;

  @Test
  void readAll_shouldReturnAllBanks() {
    Bank bank1 = new Bank();
    Bank bank2 = new Bank();
    when(bankPersistence.readAll()).thenReturn(Flux.just(bank1, bank2));

    Flux<Bank> result = bankService.readAll();

    StepVerifier.create(result)
        .expectNext(bank1)
        .expectNext(bank2)
        .verifyComplete();

    verify(bankPersistence, times(1)).readAll();
  }
}