package es.miw.tfm.invierte.core.domain.persistence;

import es.miw.tfm.invierte.core.domain.model.Membership;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Interface for Membership persistence operations.
 * Provides methods for creating, updating, deleting, and retrieving Membership entities.
 * This interface is implemented by classes that handle data access for Memberships.
 *
 * @author denilssonmn
 * @author devcastlecix
 */
@Repository
public interface MembershipPersistence {

  Mono<Membership> create(Membership membership);

  Mono<Void> deleteById(Integer id);

  Mono<Membership> update(Integer id, Membership membership);

  Mono<Membership> readById(Integer id);

  Flux<Membership> readAll();

}
