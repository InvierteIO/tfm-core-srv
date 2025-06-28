package es.miw.tfm.invierte.core.infrastructure.data.persistence;

import es.miw.tfm.invierte.core.domain.model.Bank;
import es.miw.tfm.invierte.core.domain.persistence.BankPersistence;
import es.miw.tfm.invierte.core.infrastructure.data.dao.BankRepository;
import es.miw.tfm.invierte.core.infrastructure.data.entity.BankEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Implementation of the {@link BankPersistence} interface for managing bank entities.
 * Handles the persistence logic for retrieving all banks using a JPA repository.
 * Converts between domain models and JPA entities.
 *
 * @author denilssonmn
 */
@Repository
@RequiredArgsConstructor
public class BankPersistenceImpl implements BankPersistence {

  private final BankRepository bankRepository;

  @Override
  public Flux<Bank> readAll() {
    return Flux.defer(() -> Flux.fromIterable(this.bankRepository.findAll()))
        .map(BankEntity::toBank);
  }

}
