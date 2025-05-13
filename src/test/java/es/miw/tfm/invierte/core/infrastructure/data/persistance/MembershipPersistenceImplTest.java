package es.miw.tfm.invierte.core.infrastructure.data.persistance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import es.miw.tfm.invierte.core.domain.exception.ConflictException;
import es.miw.tfm.invierte.core.domain.exception.NotFoundException;
import es.miw.tfm.invierte.core.domain.model.Membership;
import es.miw.tfm.invierte.core.infrastructure.data.dao.MembershipRepository;
import es.miw.tfm.invierte.core.infrastructure.data.entity.MembershipEntity;
import es.miw.tfm.invierte.core.infrastructure.data.persistence.MembershipPersistenceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class MembershipPersistenceImplTest {
  @InjectMocks
  private MembershipPersistenceImpl membershipPersistence;

  @Mock
  private MembershipRepository membershipRepository;

  @Test
  void shouldCreateMembership() {
    Membership membership = Membership.builder()
        .levelName("Gold")
        .shortDescription("Gold membership")
        .longDescription("Full access")
        .monthlyCost(BigDecimal.valueOf(50.00))
        .annualCost(BigDecimal.valueOf(500.00))
        .build();
    MembershipEntity entity = new MembershipEntity(membership);
    when(this.membershipRepository.findByLevel("Gold")).thenReturn(null);
    when(this.membershipRepository.save(any(MembershipEntity.class))).thenReturn(entity);

    Mono<Membership> result = membershipPersistence.create(membership);

    assertNotNull(result);
    assertEquals("Gold", result.block().getLevelName());
    verify(this.membershipRepository, times(1)).findByLevel("Gold");
    verify(this.membershipRepository, times(1)).save(any(MembershipEntity.class));
  }

  @Test
  void shouldDeleteMembershipById() {
    Integer id = 1;
    MembershipEntity entity = MembershipEntity.builder().id(id).build();
    when(this.membershipRepository.findById(id)).thenReturn(Optional.of(entity));
    doNothing().when(this.membershipRepository).deleteById(id);

    this.membershipPersistence.deleteById(id).block();

    verify(this.membershipRepository, times(1)).findById(id);
    verify(this.membershipRepository, times(1)).deleteById(id);
  }

  @Test
  void shouldUpdateMembership() {
    Integer id = 1;
    Membership membership = Membership.builder()
        .id(id)
        .levelName("Updated Level")
        .shortDescription("Updated description")
        .longDescription("Updated long description")
        .monthlyCost(BigDecimal.valueOf(60.00))
        .annualCost(BigDecimal.valueOf(600.00))
        .maxProjects(3)
        .build();
    MembershipEntity existingEntity = MembershipEntity.builder()
        .id(id)
        .level("Old Level")
        .maxProjects(2)
        .build();
    MembershipEntity updatedEntity = new MembershipEntity(membership);
    updatedEntity.setId(id);

    when(this.membershipRepository.findById(id)).thenReturn(Optional.of(existingEntity));
    when(this.membershipRepository.findByLevel("Updated Level")).thenReturn(null);
    when(this.membershipRepository.save(argThat(updatedMembership ->
            updatedMembership.getMaxProjects() == existingEntity.getMaxProjects()
        ))).thenReturn(updatedEntity);

    final var result = membershipPersistence.update(id, membership).block();

    assertNotNull(result);
    assertEquals("Updated Level", result.getLevelName());
    verify(this.membershipRepository, times(1)).findById(id);
    verify(this.membershipRepository, times(1)).findByLevel("Updated Level");
    verify(this.membershipRepository, times(1)).save(argThat(updatedMembership ->
        updatedMembership.getMaxProjects() == existingEntity.getMaxProjects()
    ));
  }

  @Test
  void shouldReadMembershipById() {
    Integer id = 1;
    MembershipEntity entity = MembershipEntity.builder()
        .id(id)
        .level("Basic")
        .overview("Basic access")
        .description("Basic membership")
        .monthlyPrice(BigDecimal.valueOf(20.00))
        .annualPrice(BigDecimal.valueOf(200.00))
        .build();
    when(this.membershipRepository.findById(id)).thenReturn(Optional.of(entity));

    Mono<Membership> result = this.membershipPersistence.readById(id);

    assertNotNull(result);
    assertEquals("Basic", result.block().getLevelName());
    verify(this.membershipRepository, times(1)).findById(id);
  }

  @Test
  void shouldReadAllMemberships() {
    MembershipEntity entity1 = MembershipEntity.builder()
        .level("Basic")
        .overview("Basic access")
        .description("Basic membership")
        .monthlyPrice(BigDecimal.valueOf(20.00))
        .annualPrice(BigDecimal.valueOf(200.00))
        .build();
    MembershipEntity entity2 = MembershipEntity.builder()
        .level("Premium")
        .overview("Premium access")
        .description("Premium membership")
        .monthlyPrice(BigDecimal.valueOf(50.00))
        .annualPrice(BigDecimal.valueOf(500.00))
        .build();
    when(this.membershipRepository.findAll()).thenReturn(Arrays.asList(entity1, entity2));

    Flux<Membership> result = this.membershipPersistence.readAll();

    assertNotNull(result);
    assertEquals(2, result.collectList().block().size());
    verify(this.membershipRepository, times(1)).findAll();
  }

  @Test
  void shouldThrowConflictExceptionWhenLevelExists() {
    Membership membership = Membership.builder().levelName("Gold").build();
    MembershipEntity existingEntity = MembershipEntity.builder().level("Gold").build();
    when(this.membershipRepository.findByLevel("Gold")).thenReturn(existingEntity);

    Mono<Membership> result = this.membershipPersistence.create(membership);

    assertNotNull(result);
    result.onErrorResume(ConflictException.class, e -> {
      assertEquals("Conflict Exception. Already Exists Membership with Level : Gold", e.getMessage());
      return Mono.empty();
    }).block();
    verify(this.membershipRepository, times(1)).findByLevel("Gold");
  }

  @Test
  void shouldThrowNotFoundExceptionWhenMembershipNotFound() {
    Integer id = 1;
    when(this.membershipRepository.findById(id)).thenReturn(Optional.empty());

    Mono<Membership> result = this.membershipPersistence.readById(id);

    assertNotNull(result);
    result.onErrorResume(NotFoundException.class, e -> {
      assertEquals("Not Found Exception. Non existent Membership with id: 1", e.getMessage());
      return Mono.empty();
    }).block();
    verify(this.membershipRepository, times(1)).findById(id);
  }
}
