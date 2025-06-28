package es.miw.tfm.invierte.core.domain.service;

import es.miw.tfm.invierte.core.domain.model.Bank;
import es.miw.tfm.invierte.core.domain.persistence.BankPersistence;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Service class for managing {@link Bank} domain operations.
 * Delegates persistence operations to the {@link BankPersistence} interface.
 * Provides reactive access to bank data.
 *
 * @author denilssonmn
 */
@Service
@RequiredArgsConstructor
public class BankService {

  private final BankPersistence bankPersistence;

  public Flux<Bank> readAll() {
    return this.bankPersistence.readAll();
  }
}
