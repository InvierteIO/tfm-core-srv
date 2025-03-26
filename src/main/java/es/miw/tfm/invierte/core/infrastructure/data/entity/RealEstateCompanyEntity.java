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

  public RealEstateCompanyEntity(RealStateCompany realStateCompany) {
    BeanUtils.copyProperties(realStateCompany, this);
  }

  public RealStateCompany toRealStateCompany() {
    RealStateCompany realStateCompany = new RealStateCompany();
    BeanUtils.copyProperties(this, realStateCompany);
    return realStateCompany;
  }

}
