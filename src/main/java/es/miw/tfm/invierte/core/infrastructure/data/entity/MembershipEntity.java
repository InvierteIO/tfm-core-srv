package es.miw.tfm.invierte.core.infrastructure.data.entity;

import es.miw.tfm.invierte.core.domain.model.Membership;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing a Membership in the database.
 * This class maps the `membership` table and provides fields for membership details,
 * such as level, pricing, and limits on advisors and projects.
 *
 * <p>It includes methods to convert between the entity and domain model representations.
 *
 * @see Membership
 *
 * @author denilssonmn
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "membership")
public class MembershipEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(unique = true, nullable = false, length = 50)
  private String level;
  @Column(nullable = false, length = 255)
  private String overview;
  @Column(columnDefinition = "TEXT")
  private String description;
  @Column(name = "monthly_price", nullable = false, precision = 10, scale = 2)
  private BigDecimal monthlyPrice;
  @Column(name = "annual_price", nullable = false, precision = 10, scale = 2)
  private BigDecimal annualPrice;
  @Column(name = "max_advisors", nullable = false)
  private int maxAdvisors;
  @Column(name = "max_projects", nullable = false)
  private int maxProjects;

  /**
   * Constructs a `MembershipEntity` from a `Membership` domain model.
   *
   * @param membership the domain model to convert
   */
  public MembershipEntity(Membership membership) {
    this.id = membership.getId();
    this.level = membership.getLevelName();
    this.overview = membership.getShortDescription();
    this.description = membership.getLongDescription();
    this.monthlyPrice = membership.getMonthlyCost();
    this.annualPrice = membership.getAnnualCost();
    this.maxAdvisors = membership.getMaxRealtors();
    this.maxProjects = membership.getMaxProjects();
  }

  /**
   * Converts this `MembershipEntity` to a `Membership` domain model.
   *
   * @return the converted domain model
   */
  public Membership toMembership() {
    Membership membership = new Membership();
    membership.setId(id);
    membership.setLevelName(level);
    membership.setShortDescription(overview);
    membership.setLongDescription(description);
    membership.setMonthlyCost(monthlyPrice);
    membership.setAnnualCost(annualPrice);
    membership.setMaxRealtors(maxAdvisors);
    membership.setMaxProjects(maxProjects);
    return membership;
  }
}
