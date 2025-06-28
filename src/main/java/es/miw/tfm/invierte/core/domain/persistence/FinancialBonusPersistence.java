package es.miw.tfm.invierte.core.domain.persistence;

import es.miw.tfm.invierte.core.domain.model.FinancialBonus;
import reactor.core.publisher.Flux;

/**
 * Persistence interface for managing {@link FinancialBonus} entities.
 * Defines methods for retrieving financial bonus data from the underlying data source.
 * Provides reactive access to financial bonus records.
 *
 * @author denilssonmn
 */
public interface FinancialBonusPersistence {

  Flux<FinancialBonus> readAll();
}
