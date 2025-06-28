package es.miw.tfm.invierte.core.domain.persistence;

import es.miw.tfm.invierte.core.domain.model.Bank;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Persistence interface for managing {@link Bank} entities.
 * Defines methods for retrieving bank data from the underlying data source.
 * Provides reactive access to bank records.
 *
 * @author denilssonmn
 */
@Repository
public interface BankPersistence {

  Flux<Bank> readAll();

}
