package es.miw.tfm.invierte.core.infrastructure.data.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import es.miw.tfm.invierte.core.domain.model.Membership;
import org.junit.jupiter.api.Test;

class MembershipEntityTest {

  @Test
  void shouldConvertToMembership() {
    MembershipEntity entity = MembershipEntity.builder()
        .id(1)
        .level("Gold")
        .overview("Premium membership")
        .description("Access to all features")
        .monthlyPrice(BigDecimal.valueOf(99.99))
        .annualPrice(BigDecimal.valueOf(999.99))
        .maxAdvisors(5)
        .maxProjects(10)
        .build();

    Membership membership = entity.toMembership();

    assertNotNull(membership);
    assertEquals(entity.getLevel(), membership.getLevelName());
    assertEquals(entity.getOverview(), membership.getShortDescription());
    assertEquals(entity.getDescription(), membership.getLongDescription());
    assertEquals(entity.getMonthlyPrice(), membership.getMonthlyCost());
    assertEquals(entity.getAnnualPrice(), membership.getAnnualCost());
    assertEquals(entity.getMaxAdvisors(), membership.getMaxRealtors());
  }

  @Test
  void shouldConvertFromMembership() {
    Membership membership = new Membership();
    membership.setLevelName("Silver");
    membership.setShortDescription("Basic membership");
    membership.setLongDescription("Limited access to features");
    membership.setMonthlyCost(BigDecimal.valueOf(49.99));
    membership.setAnnualCost(BigDecimal.valueOf(499.99));
    membership.setMaxRealtors(2);

    MembershipEntity entity = new MembershipEntity(membership);

    assertNotNull(entity);
    assertEquals(membership.getLevelName(), entity.getLevel());
    assertEquals(membership.getShortDescription(), entity.getOverview());
    assertEquals(membership.getLongDescription(), entity.getDescription());
    assertEquals(membership.getMonthlyCost(), entity.getMonthlyPrice());
    assertEquals(membership.getAnnualCost(), entity.getAnnualPrice());
    assertEquals(membership.getMaxRealtors(), entity.getMaxAdvisors());
  }
}
