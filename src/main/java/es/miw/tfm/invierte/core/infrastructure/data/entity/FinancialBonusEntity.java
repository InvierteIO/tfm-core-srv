package es.miw.tfm.invierte.core.infrastructure.data.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

  @OneToMany(mappedBy = "financialBonus", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<FinancialBonusTypeEntity> financialBonusTypeEntities;
}
