package es.miw.tfm.invierte.core.infrastructure.data.entity;

import es.miw.tfm.invierte.core.domain.model.FinancialBonus;
import es.miw.tfm.invierte.core.domain.model.FinancialBonusType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * JPA entity representing a financial bonus in the system.
 * Maps the domain model to the database table `financial_bonus`.
 * Stores information about the bonus's name.
 *
 * @author denilssonmn
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "financial_bonus")
public class FinancialBonusEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 4, nullable = false, unique = true)
  private String code;

  @Column(length = 100)
  private String name;

  @OneToMany(mappedBy = "financialBonus", cascade = CascadeType.ALL, orphanRemoval = true,
      fetch = FetchType.EAGER)
  private List<FinancialBonusTypeEntity> financialBonusTypeEntities;

  /**
   * Converts this entity to its corresponding domain model {@link FinancialBonus}.
   * Copies properties and populates the list of bonus types from related entities.
   *
   * @return the domain model representation of this entity
   */
  public FinancialBonus toFinancialBonus() {
    FinancialBonus financialBonus = new FinancialBonus();
    BeanUtils.copyProperties(this, financialBonus);

    Optional.ofNullable(this.financialBonusTypeEntities)
        .orElseGet(ArrayList::new)
        .forEach(financialBonusTypeEntity -> {
          FinancialBonusType financialBonusType = new FinancialBonusType();
          BeanUtils.copyProperties(financialBonusTypeEntity, financialBonusType);
          financialBonus.getTypes().add(financialBonusType);
        });

    return financialBonus;
  }
}
