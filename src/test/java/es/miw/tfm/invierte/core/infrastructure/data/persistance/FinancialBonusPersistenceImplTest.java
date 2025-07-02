package es.miw.tfm.invierte.core.infrastructure.data.persistence;

import es.miw.tfm.invierte.core.domain.model.FinancialBonus;
import es.miw.tfm.invierte.core.infrastructure.data.dao.FinancialBonusRepository;
import es.miw.tfm.invierte.core.infrastructure.data.entity.FinancialBonusEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class FinancialBonusPersistenceImplTest {

  private FinancialBonusRepository financialBonusRepository;
  private FinancialBonusPersistenceImpl financialBonusPersistence;

  @BeforeEach
  void setUp() {
    financialBonusRepository = mock(FinancialBonusRepository.class);
    financialBonusPersistence = new FinancialBonusPersistenceImpl(financialBonusRepository);
  }

  @Test
  void readAll_shouldReturnAllFinancialBonuses() {
    FinancialBonusEntity entity1 = mock(FinancialBonusEntity.class);
    FinancialBonusEntity entity2 = mock(FinancialBonusEntity.class);
    FinancialBonus bonus1 = new FinancialBonus();
    FinancialBonus bonus2 = new FinancialBonus();

    when(entity1.toFinancialBonus()).thenReturn(bonus1);
    when(entity2.toFinancialBonus()).thenReturn(bonus2);

    List<FinancialBonusEntity> entities = Arrays.asList(entity1, entity2);
    when(financialBonusRepository.findAll()).thenReturn(entities);

    Flux<FinancialBonus> result = financialBonusPersistence.readAll();

    StepVerifier.create(result)
        .expectNext(bonus1)
        .expectNext(bonus2)
        .verifyComplete();

    verify(financialBonusRepository, times(1)).findAll();
    verify(entity1, times(1)).toFinancialBonus();
    verify(entity2, times(1)).toFinancialBonus();
  }

  @Test
  void readAll_shouldReturnEmptyWhenNoFinancialBonuses() {
    when(financialBonusRepository.findAll()).thenReturn(List.of());

    Flux<FinancialBonus> result = financialBonusPersistence.readAll();

    StepVerifier.create(result)
        .verifyComplete();

    verify(financialBonusRepository, times(1)).findAll();
  }
}