package es.miw.tfm.invierte.core.domain.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import es.miw.tfm.invierte.core.domain.model.Membership;
import es.miw.tfm.invierte.core.domain.persistence.MembershipPersistence;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class MembershipServiceTest {

    private static final Integer ID = 1;
    private static final String LEVEL_NAME = "Level Name";
    private static final String SHORT_DESCRIPTION = "Short description";
    private static final String LONG_DESCRIPTION = "Long description";
    private static final BigDecimal MONTHLY_COST = new BigDecimal("100.00");
    private static final BigDecimal ANNUAL_COST = new BigDecimal("1000.00");

    @InjectMocks
    private MembershipService membershipService;

    @Mock
    private MembershipPersistence membershipPersistence;

    @Test
    void create_whenMembershipReceived_thenReturnCreated() {
        Membership inputMembership = Membership.builder()
                .levelName(LEVEL_NAME)
                .shortDescription(SHORT_DESCRIPTION)
                .longDescription(LONG_DESCRIPTION)
                .monthlyCost(MONTHLY_COST)
                .annualCost(ANNUAL_COST)
                .build();
        Membership savedMembership = Membership.builder()
                .levelName(LEVEL_NAME)
                .shortDescription(SHORT_DESCRIPTION)
                .longDescription(LONG_DESCRIPTION)
                .monthlyCost(MONTHLY_COST)
                .annualCost(ANNUAL_COST)
                .build();
        Mono<Membership> savedMono = Mono.just(savedMembership);
        Mockito.when(this.membershipPersistence.create(Mockito.any(Membership.class)))
                .thenReturn(savedMono);

        Mono<Membership> resultMono = this.membershipService.create(inputMembership);

        verify(this.membershipPersistence, times(1)).create(inputMembership);
        Assertions.assertNotNull(resultMono);
        Membership result = resultMono.block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(inputMembership, result);
    }

    @Test
    void read_whenIdExists_thenReturnMembership() {
        Membership expectedMembership = Membership.builder()
                .id(ID)
                .levelName(LEVEL_NAME)
                .shortDescription(SHORT_DESCRIPTION)
                .longDescription(LONG_DESCRIPTION)
                .monthlyCost(MONTHLY_COST)
                .annualCost(ANNUAL_COST)
                .build();
        Mono<Membership> expectedMono = Mono.just(expectedMembership);
        Mockito.when(this.membershipPersistence.readById(ID))
                .thenReturn(expectedMono);

        Mono<Membership> resultMono = this.membershipService.read(ID);

        verify(this.membershipPersistence, times(1)).readById(ID);
        Assertions.assertNotNull(resultMono);
        Membership result = resultMono.block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedMono.block(), result);
    }

    @Test
    void update_whenIdExists_thenReturnUpdatedMembership() {
        Membership inputMembership = Membership.builder()
                .id(ID)
                .levelName(LEVEL_NAME)
                .shortDescription(SHORT_DESCRIPTION)
                .longDescription(LONG_DESCRIPTION)
                .monthlyCost(MONTHLY_COST)
                .annualCost(ANNUAL_COST)
                .build();
        Membership updatedMembership = Membership.builder()
                .id(ID)
                .levelName(LEVEL_NAME)
                .shortDescription(SHORT_DESCRIPTION)
                .longDescription(LONG_DESCRIPTION)
                .monthlyCost(MONTHLY_COST)
                .annualCost(ANNUAL_COST)
                .build();
        Mono<Membership> updatedMono = Mono.just(updatedMembership);
        Mockito.when(this.membershipPersistence.update(ID, inputMembership))
                .thenReturn(updatedMono);

        Mono<Membership> resultMono = this.membershipService.update(ID, inputMembership);

        verify(this.membershipPersistence, times(1)).update(ID, inputMembership);
        Assertions.assertNotNull(resultMono);
        Membership result = resultMono.block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(updatedMono.block(), result);
    }

    @Test
    void delete_whenIdExists_thenDeleteSuccessfully() {
        Mockito.when(this.membershipPersistence.deleteById(ID))
                .thenReturn(Mono.empty());

        Mono<Void> resultMono = this.membershipService.delete(ID);

        verify(this.membershipPersistence, times(1)).deleteById(ID);
        Assertions.assertNotNull(resultMono);
        Void result = resultMono.block();
        Assertions.assertNull(result);
    }

    @Test
    void readAll_whenCalled_thenReturnAllMemberships() {
        Membership membership1 = Membership.builder()
                .id(ID)
                .levelName(LEVEL_NAME)
                .shortDescription(SHORT_DESCRIPTION)
                .longDescription(LONG_DESCRIPTION)
                .monthlyCost(MONTHLY_COST)
                .annualCost(ANNUAL_COST)
                .build();
        Membership membership2 = Membership.builder()
                .id(2)
                .levelName("Another Level")
                .shortDescription("Another short description")
                .longDescription("Another long description")
                .monthlyCost(new BigDecimal("200.00"))
                .annualCost(new BigDecimal("2000.00"))
                .build();
        Flux<Membership> flux = Flux.just(membership1, membership2);
        Mockito.when(this.membershipPersistence.readAll())
                .thenReturn(flux);

        Flux<Membership> resultFlux = this.membershipService.read();

        verify(this.membershipPersistence, times(1)).readAll();
        Assertions.assertNotNull(resultFlux);
        List<Membership> membershipList = resultFlux.collectList().block();
        Assertions.assertNotNull(membershipList);
        Assertions.assertEquals(2, membershipList.size());
        Assertions.assertTrue(membershipList.contains(membership1));
        Assertions.assertTrue(membershipList.contains(membership2));
    }
}
