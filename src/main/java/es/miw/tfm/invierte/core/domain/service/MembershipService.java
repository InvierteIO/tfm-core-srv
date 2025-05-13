package es.miw.tfm.invierte.core.domain.service;

import es.miw.tfm.invierte.core.domain.model.Membership;
import es.miw.tfm.invierte.core.domain.persistence.MembershipPersistence;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service class for Membership operations.
 * Provides methods for creating, updating, deleting, and retrieving Membership entities.
 * This class acts as a bridge between the controller and persistence layers.
 *
 * @author denilssonmn
 * @author devcastlecix
 */
@Service
@RequiredArgsConstructor
public class MembershipService {

  private final MembershipPersistence membershipPersistence;

  public Mono<Membership> create(Membership membership) {
    return membershipPersistence.create(membership);
  }

  public Mono<Void> delete(Integer id) {
    return this.membershipPersistence.deleteById(id);
  }

  public Mono<Membership> update(Integer id, Membership membership) {
    return this.membershipPersistence.update(id, membership);
  }

  public Mono<Membership> read(Integer id) {
    return this.membershipPersistence.readById(id);
  }

  public Flux<Membership> read() {
    return this.membershipPersistence.readAll();
  }

}
