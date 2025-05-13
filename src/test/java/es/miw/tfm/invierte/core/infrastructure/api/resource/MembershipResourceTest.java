package es.miw.tfm.invierte.core.infrastructure.api.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;

import es.miw.tfm.invierte.core.domain.model.Membership;
import es.miw.tfm.invierte.core.domain.service.MembershipService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class MembershipResourceTest {

  @InjectMocks
  private MembershipResource membershipResource;

  @Mock
  private MembershipService membershipService;

  @Test
  void shouldCreateMembership() {
    Membership membership = Membership.builder()
        .levelName("Premium")
        .shortDescription("Premium access")
        .longDescription("Full premium membership")
        .monthlyCost(BigDecimal.valueOf(99.99))
        .annualCost(BigDecimal.valueOf(999.99))
        .build();
    when(this.membershipService.create(membership)).thenReturn(Mono.just(membership));

    Mono<Membership> response = this.membershipResource.create(membership);

    assertNotNull(response);
    assertEquals("Premium", response.block().getLevelName());
    verify(this.membershipService, times(1)).create(membership);
  }

  @Test
  void shouldDeleteMembership() {
    Integer id = 1;
    when(this.membershipService.delete(id)).thenReturn(Mono.empty());

    Mono<Void> response = this.membershipResource.delete(id);

    assertNotNull(response);
    verify(this.membershipService, times(1)).delete(id);
  }

  @Test
  void shouldUpdateMembership() {
    Integer id = 1;
    Membership membership = Membership.builder()
        .id(id)
        .levelName("Updated Level")
        .shortDescription("Updated description")
        .longDescription("Updated long description")
        .monthlyCost(BigDecimal.valueOf(49.99))
        .annualCost(BigDecimal.valueOf(499.99))
        .build();
    when(this.membershipService.update(id, membership)).thenReturn(Mono.just(membership));

    Mono<Membership> response = this.membershipResource.update(id, membership);

    assertNotNull(response);
    assertEquals("Updated Level", response.block().getLevelName());
    verify(this.membershipService, times(1)).update(id, membership);
  }

  @Test
  void shouldReadMembership() {
    Integer id = 1;
    Membership membership = Membership.builder()
        .id(id)
        .levelName("Basic")
        .shortDescription("Basic access")
        .longDescription("Basic membership")
        .monthlyCost(BigDecimal.valueOf(19.99))
        .annualCost(BigDecimal.valueOf(199.99))
        .build();
    when(this.membershipService.read(id)).thenReturn(Mono.just(membership));

    Mono<Membership> response = this.membershipResource.read(id);

    assertNotNull(response);
    assertEquals("Basic", response.block().getLevelName());
    verify(this.membershipService, times(1)).read(id);
  }

  @Test
  void shouldReadAllMemberships() {
    Membership membership1 = Membership.builder()
        .levelName("Basic")
        .shortDescription("Basic access")
        .longDescription("Basic membership")
        .monthlyCost(BigDecimal.valueOf(19.99))
        .annualCost(BigDecimal.valueOf(199.99))
        .build();
    Membership membership2 = Membership.builder()
        .levelName("Premium")
        .shortDescription("Premium access")
        .longDescription("Full premium membership")
        .monthlyCost(BigDecimal.valueOf(99.99))
        .annualCost(BigDecimal.valueOf(999.99))
        .build();
    when(this.membershipService.read()).thenReturn(Flux.fromIterable(Arrays.asList(membership1, membership2)));

    Flux<Membership> response = this.membershipResource.readAll();

    assertNotNull(response);
    assertEquals(2, response.collectList().block().size());
    verify(this.membershipService, times(1)).read();
  }
}
