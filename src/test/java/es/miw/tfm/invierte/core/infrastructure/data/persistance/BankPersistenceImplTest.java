package es.miw.tfm.invierte.core.infrastructure.data.persistance;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import es.miw.tfm.invierte.core.domain.model.Bank;
import es.miw.tfm.invierte.core.infrastructure.data.dao.BankRepository;
import es.miw.tfm.invierte.core.infrastructure.data.entity.BankEntity;
import es.miw.tfm.invierte.core.infrastructure.data.persistence.BankPersistenceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class BankPersistenceImplTest {

  private BankRepository bankRepository;
  private BankPersistenceImpl bankPersistence;

  @BeforeEach
  void setUp() {
    bankRepository = mock(BankRepository.class);
    bankPersistence = new BankPersistenceImpl(bankRepository);
  }

  @Test
  void readAll_shouldReturnAllBanks() {
    BankEntity entity1 = mock(BankEntity.class);
    BankEntity entity2 = mock(BankEntity.class);
    Bank bank1 = new Bank();
    Bank bank2 = new Bank();

    when(entity1.toBank()).thenReturn(bank1);
    when(entity2.toBank()).thenReturn(bank2);

    List<BankEntity> entities = Arrays.asList(entity1, entity2);
    when(bankRepository.findAll()).thenReturn(entities);

    Flux<Bank> result = bankPersistence.readAll();

    StepVerifier.create(result)
        .expectNext(bank1)
        .expectNext(bank2)
        .verifyComplete();

    verify(bankRepository, times(1)).findAll();
    verify(entity1, times(1)).toBank();
    verify(entity2, times(1)).toBank();
  }

  @Test
  void readAll_shouldReturnEmptyWhenNoBanks() {
    when(bankRepository.findAll()).thenReturn(List.of());

    Flux<Bank> result = bankPersistence.readAll();

    StepVerifier.create(result)
        .verifyComplete();

    verify(bankRepository, times(1)).findAll();
  }
}