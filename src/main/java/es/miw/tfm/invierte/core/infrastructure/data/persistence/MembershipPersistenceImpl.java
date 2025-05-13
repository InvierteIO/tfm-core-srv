package es.miw.tfm.invierte.core.infrastructure.data.persistence;

import es.miw.tfm.invierte.core.domain.exception.ConflictException;
import es.miw.tfm.invierte.core.domain.exception.NotFoundException;
import es.miw.tfm.invierte.core.domain.model.Membership;
import es.miw.tfm.invierte.core.domain.persistence.MembershipPersistence;
import es.miw.tfm.invierte.core.infrastructure.data.dao.MembershipRepository;
import es.miw.tfm.invierte.core.infrastructure.data.entity.MembershipEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * Implementation of the MembershipPersistence interface.
 * This class provides methods for creating, reading, updating, and deleting Membership entities.
 * It interacts with the MembershipRepository to perform database operations.
 *
 * <p>The class ensures that business rules are enforced, such as preventing duplicate
 * membership levels and handling non-existent entities.
 *
 * <p>This class uses reactive programming with Project Reactor to handle asynchronous operations.
 *
 * @see MembershipPersistence
 * @see MembershipRepository
 * @see MembershipEntity
 *
 * @author denilssonmn
 */
@Repository
@RequiredArgsConstructor
public class MembershipPersistenceImpl implements MembershipPersistence {

  private final MembershipRepository membershipRepository;

  @Override
  public Mono<Membership> create(Membership membership) {
    return this.assertLevelNotExist(membership.getLevelName())
            .then(Mono.defer(() -> Mono.just(
                this.membershipRepository.save(new MembershipEntity(membership)))
                    .subscribeOn(Schedulers.boundedElastic())))
            .map(MembershipEntity::toMembership);
  }

  @Override
  public Mono<Void> deleteById(Integer id) {
    return this.assertMembershipEntityExists(id)
        .then(Mono.fromRunnable(() -> this.membershipRepository.deleteById(id)));
  }

  @Override
  public Mono<Membership> update(Integer id, Membership membership) {
    return this.assertMembershipEntityExists(id)
            .flatMap(existingEntity ->
               assertLevelUnique(existingEntity.getId(), membership.getLevelName())
                  .then(Mono.just(existingEntity)))
            .map(existingEntity -> {
              MembershipEntity membershipUpdateDb = new MembershipEntity(membership);
              membershipUpdateDb.setId(existingEntity.getId());
              membershipUpdateDb.setMaxProjects(existingEntity.getMaxProjects());
              return this.membershipRepository.save(membershipUpdateDb);
            })
            .map(MembershipEntity::toMembership);
  }

  @Override
  public Mono<Membership> readById(Integer id) {
    return this.assertMembershipEntityExists(id)
            .map(MembershipEntity::toMembership);
  }

  @Override
  public Flux<Membership> readAll() {
    return Flux.defer(() -> Flux.fromIterable(this.membershipRepository.findAll()))
            .map(MembershipEntity::toMembership);
  }

  private Mono<Void> assertLevelUnique(Integer currentId, String level) {
    return Mono.justOrEmpty(this.membershipRepository.findByLevel(level))
            .filter(conflict -> !conflict.getId().equals(currentId))
            .flatMap(conflict ->
                Mono.error(
                    new ConflictException("Already exists Membership with Level: " + level)))
            .then();
  }

  private Mono<Void> assertLevelNotExist(String level) {
    return Mono.defer(() -> Mono.justOrEmpty(this.membershipRepository.findByLevel(level)))
            .flatMap(memberships ->
                    Mono.error(
                        new ConflictException("Already Exists Membership with Level : " + level))
            )
            .then();
  }

  private Mono<MembershipEntity> assertMembershipEntityExists(Integer id) {
    return Mono.justOrEmpty(this.membershipRepository.findById(id))
            .switchIfEmpty(Mono.error(
                new NotFoundException("Non existent Membership with id: " + id)));
  }
}
