package es.miw.tfm.invierte.core.infrastructure.api.resource;

import es.miw.tfm.invierte.core.BaseContainerIntegration;
import es.miw.tfm.invierte.core.configuration.JwtService;
import es.miw.tfm.invierte.core.domain.model.Membership;
import es.miw.tfm.invierte.core.infrastructure.data.dao.MembershipRepository;
import es.miw.tfm.invierte.core.infrastructure.data.entity.MembershipEntity;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ApiTestConfig
class MembershipResourceIT extends BaseContainerIntegration {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private MembershipRepository membershipRepository;

    @BeforeAll
    static void setup() {
        postgreSQLContainer.start();
    }

    @AfterAll
    static void cleanup() {
        postgreSQLContainer.stop();
    }

    @Test
    void create_whenValidData_thenReturnOK() {
        this.deleteMemberships();
        Membership membership = Membership.builder()
                .levelName("Premium Level")
                .shortDescription("Premium access features")
                .longDescription("Full premium membership with all features")
                .monthlyCost(new BigDecimal("99.99"))
                .annualCost(new BigDecimal("999.99"))
                .build();
        String token = this.jwtService.createToken("test@test.com", "test", "OWNER", "OWNER");
        String bearerToken = "Bearer " + token;
        this.webTestClient.post().uri(MembershipResource.MEMBERSHIPS)
                .header("Authorization", bearerToken)
                .bodyValue(membership)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Membership.class)
                .value(response -> {
                    Assertions.assertNotNull(response);
                    Assertions.assertNotNull(response.getId());
                    Assertions.assertEquals(membership.getLevelName(), response.getLevelName());
                    Assertions.assertEquals(membership.getShortDescription(), response.getShortDescription());
                    Assertions.assertEquals(membership.getLongDescription(), response.getLongDescription());
                    Assertions.assertEquals(0, membership.getMonthlyCost().compareTo(response.getMonthlyCost()));
                    Assertions.assertEquals(0, membership.getAnnualCost().compareTo(response.getAnnualCost()));
                });
    }

    @Test
    void update_whenValidData_thenReturnOK() {
        this.deleteMemberships();
        MembershipEntity existingEntity = this.membershipRepository.save(MembershipEntity.builder()
                .level("Basic Level")
                .overview("Basic access features")
                .description("Basic membership with limited features")
                .monthlyPrice(new BigDecimal("10.00"))
                .annualPrice(new BigDecimal("100.00"))
                .build());
        Integer id = existingEntity.getId();
        String token = this.jwtService.createToken("test@test.com", "test", "OWNER", "OWNER");
        String bearerToken = "Bearer " + token;
        Membership updatedMembership = Membership.builder()
                .id(id)
                .levelName("Basic Level - Updated")
                .shortDescription("Basic access features (updated)")
                .longDescription("Basic membership with limited features (updated)")
                .monthlyCost(new BigDecimal("15.00"))
                .annualCost(new BigDecimal("150.00"))
                .build();
        this.webTestClient.put().uri(MembershipResource.MEMBERSHIPS + MembershipResource.MEMBERSHIP_ID, id)
                .header("Authorization", bearerToken)
                .bodyValue(updatedMembership)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Membership.class)
                .value(response -> {
                    Assertions.assertNotNull(response);
                    Assertions.assertEquals(id, response.getId());
                    Assertions.assertEquals(updatedMembership.getLevelName(), response.getLevelName());
                    Assertions.assertEquals(updatedMembership.getShortDescription(), response.getShortDescription());
                    Assertions.assertEquals(updatedMembership.getLongDescription(), response.getLongDescription());
                    Assertions.assertEquals(0, updatedMembership.getMonthlyCost().compareTo(response.getMonthlyCost()));
                    Assertions.assertEquals(0, updatedMembership.getAnnualCost().compareTo(response.getAnnualCost()));
                });
    }

    @Test
    void read_whenExistingId_thenReturnOK() {
        this.deleteMemberships();
        MembershipEntity existingEntity = this.membershipRepository.save(MembershipEntity.builder()
                .level("Standard Level")
                .overview("Standard access features")
                .description("Standard membership with common features")
                .monthlyPrice(new BigDecimal("49.99"))
                .annualPrice(new BigDecimal("499.99"))
                .build());
        Integer id = existingEntity.getId();
        String token = this.jwtService.createToken("test@test.com", "test", "OWNER", "OWNER");
        String bearerToken = "Bearer " + token;
        this.webTestClient.get().uri(MembershipResource.MEMBERSHIPS + MembershipResource.MEMBERSHIP_ID, id)
                .header("Authorization", bearerToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Membership.class)
                .value(response -> {
                    Assertions.assertNotNull(response);
                    Assertions.assertEquals(existingEntity.getLevel(), response.getLevelName());
                });
    }

    @Test
    void readAll_whenRequested_thenReturnOK() {
        this.deleteMemberships();
        MembershipEntity member1 = this.membershipRepository.save(MembershipEntity.builder()
                .level("Level 1")
                .overview("Level 1 features")
                .description("Level 1 full description")
                .monthlyPrice(new BigDecimal("20.00"))
                .annualPrice(new BigDecimal("200.00"))
                .build());
        MembershipEntity member2 = this.membershipRepository.save(MembershipEntity.builder()
                .level("Level 2")
                .overview("Level 2 features")
                .description("Level 2 full description")
                .monthlyPrice(new BigDecimal("30.00"))
                .annualPrice(new BigDecimal("300.00"))
                .build());
        String token = this.jwtService.createToken("test@test.com", "test", "OWNER", "OWNER");
        String bearerToken = "Bearer " + token;
        this.webTestClient.get().uri(MembershipResource.MEMBERSHIPS)
                .header("Authorization", bearerToken)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Membership.class)
                .value(membershipList -> {
                    Assertions.assertNotNull(membershipList);
                    Assertions.assertFalse(membershipList.isEmpty());
                    Optional<Membership> opt1 = membershipList.stream()
                            .filter(m -> "Level 1".equals(m.getLevelName()))
                            .findFirst();
                    Optional<Membership> opt2 = membershipList.stream()
                            .filter(m -> "Level 2".equals(m.getLevelName()))
                            .findFirst();
                    Assertions.assertTrue(opt1.isPresent());
                    Assertions.assertTrue(opt2.isPresent());
                    opt1.ifPresent(m -> {
                        Assertions.assertEquals(member1.getOverview(), m.getShortDescription());
                        Assertions.assertEquals(0, member1.getMonthlyPrice().compareTo(m.getMonthlyCost()));
                    });
                    opt2.ifPresent(m -> {
                        Assertions.assertEquals(member2.getOverview(), m.getShortDescription());
                        Assertions.assertEquals(0, member2.getAnnualPrice().compareTo(m.getAnnualCost()));
                    });
                });
    }

    @Test
    void delete_whenExistingId_thenReturnOK() {
        this.deleteMemberships();
        MembershipEntity existingEntity = this.membershipRepository.save(MembershipEntity.builder()
                .level("Temp Level")
                .overview("Temporary membership")
                .description("Temporary membership to be deleted")
                .monthlyPrice(new BigDecimal("5.00"))
                .annualPrice(new BigDecimal("50.00"))
                .build());
        Integer id = existingEntity.getId();
        String token = this.jwtService.createToken("test@test.com", "test", "OWNER", "OWNER");
        String bearerToken = "Bearer " + token;
        this.webTestClient.delete().uri(MembershipResource.MEMBERSHIPS + MembershipResource.MEMBERSHIP_ID, id)
                .header("Authorization", bearerToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();
        Assertions.assertFalse(this.membershipRepository.findById(id).isPresent());
    }

    private void deleteMemberships() {
        this.membershipRepository.deleteAll();
    }
}
