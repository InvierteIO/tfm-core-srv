package es.miw.tfm.invierte.core.domain.service;

import es.miw.tfm.invierte.core.domain.model.FinancialBonus;
import es.miw.tfm.invierte.core.domain.persistence.FinancialBonusPersistence;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Service class for managing {@link FinancialBonus} domain operations.
 * Delegates persistence operations to the {@link FinancialBonusPersistence} interface.
 * Provides reactive access to financial bonus data.
 *
 * @author denilssonmn
 */
@Service
@RequiredArgsConstructor
public class FinancialBonusService {

  private final FinancialBonusPersistence financialBonusPersistence;

  public Flux<FinancialBonus> readAll() {
    return this.financialBonusPersistence.readAll();
  }
}
