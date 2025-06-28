package es.miw.tfm.invierte.core.infrastructure.data.persistence;

import es.miw.tfm.invierte.core.domain.model.FinancialBonus;
import es.miw.tfm.invierte.core.domain.persistence.FinancialBonusPersistence;
import es.miw.tfm.invierte.core.infrastructure.data.dao.FinancialBonusRepository;
import es.miw.tfm.invierte.core.infrastructure.data.entity.FinancialBonusEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Implementation of the {@link FinancialBonusPersistence} interface for managing
 * financial bonus entities.
 * Handles the persistence logic for retrieving all financial bonuses using a JPA repository.
 * Converts between domain models and JPA entities.
 *
 * @author denilssonmn
 */
@Repository
@RequiredArgsConstructor
public class FinancialBonusPersistenceImpl implements FinancialBonusPersistence {

  private final FinancialBonusRepository financialBonusRepository;

  @Override
  public Flux<FinancialBonus> readAll() {
    return Flux.defer(() -> Flux.fromIterable(this.financialBonusRepository.findAll()))
        .map(FinancialBonusEntity::toFinancialBonus);
  }
}
