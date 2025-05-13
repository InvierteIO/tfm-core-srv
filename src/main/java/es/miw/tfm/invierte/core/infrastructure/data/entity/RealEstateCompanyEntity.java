package es.miw.tfm.invierte.core.infrastructure.data.entity;

import es.miw.tfm.invierte.core.domain.model.RealStateCompany;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * Entity class representing a Real Estate Company in the database.
 * This class maps the `real_state_company` table and provides fields for company details,
 * such as name, tax identification number, address, phone, and allowed member quantity.
 *
 * <p>It includes methods to convert between the entity and domain model representations.
 *
 * @see RealStateCompany
 *
 * @author denilssonmn
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "real_state_company")
public class RealEstateCompanyEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String name;

  @Column(unique = true, nullable = false)
  private String taxIdentificationNumber;

  private String address;

  private String phone;

  private Integer allowedMemberQuantity;

  /**
   * Constructs a `RealEstateCompanyEntity` from a `RealStateCompany` domain model.
   *
   * @param realStateCompany the domain model to convert
   */
  public RealEstateCompanyEntity(RealStateCompany realStateCompany) {
    BeanUtils.copyProperties(realStateCompany, this);
  }

  /**
   * Converts this `RealEstateCompanyEntity` to a `RealStateCompany` domain model.
   *
   * @return the converted domain model
   */
  public RealStateCompany toRealStateCompany() {
    RealStateCompany realStateCompany = new RealStateCompany();
    BeanUtils.copyProperties(this, realStateCompany);
    return realStateCompany;
  }

}
