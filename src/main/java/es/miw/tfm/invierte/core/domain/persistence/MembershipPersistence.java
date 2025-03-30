package es.miw.tfm.invierte.core.domain.persistence;

import es.miw.tfm.invierte.core.domain.model.Membership;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface MembershipPersistence {

    Mono<Membership> create(Membership membership);

    Mono<Void> deleteById(Integer id);

    Mono<Membership> update(Integer id, Membership membership);

    Mono<Membership> readById(Integer id);

    Flux<Membership> readAll();

}
