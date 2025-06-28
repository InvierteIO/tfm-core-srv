package es.miw.tfm.invierte.core.infrastructure.data.entity;

import es.miw.tfm.invierte.core.domain.model.FinancialBonusType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * JPA entity representing a financial bonus type in the system.
 * Maps the domain model to the database table `financial_bonus_type`.
 * Stores information about the bonus type's code, name, data type,
 * requirement status, its associated financial bonus, and related subproject bonus types.
 *
 * @author denilssonmn
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "financial_bonus_type")
public class FinancialBonusTypeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 4, nullable = false, unique = true)
  private String code;

  @Column(length = 50)
  private String name;

  @Column(name = "data_type", length = 20)
  private String dataType;

  private Boolean required;

  @ManyToOne
  @JoinColumn(name = "financial_bonus_id", referencedColumnName = "id")
  private FinancialBonusEntity financialBonus;

  @OneToMany(mappedBy = "financialBonusType", cascade = CascadeType.ALL, orphanRemoval = true)
  List<SubProjectBonusTypeEntity> subProjectBonusTypes;

  /**
   * Converts this entity to a domain FinancialBonusType object.
   * Copies properties from the entity to the domain model.
   *
   * @return the corresponding FinancialBonusType domain object
   */
  public FinancialBonusType toFinancialBonusType() {
    FinancialBonusType financialBonusType = new FinancialBonusType();
    BeanUtils.copyProperties(this, financialBonusType);
    return financialBonusType;
  }
}
