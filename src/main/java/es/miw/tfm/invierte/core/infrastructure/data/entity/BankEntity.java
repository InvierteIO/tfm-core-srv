package es.miw.tfm.invierte.core.infrastructure.data.entity;

import es.miw.tfm.invierte.core.domain.model.Bank;
import es.miw.tfm.invierte.core.domain.model.enums.FinancialEntityType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import org.springframework.beans.BeanUtils;

/**
 * JPA entity representing a Bank in the system.
 * Maps the domain model to the database table "bank".
 * Stores information about the bank's name and its financial entity type.
 *
 * @author denilssonmn
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bank")
public class BankEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 100)
  private String name;

  @Column(name = "financial_entity_type", length = 15)
  @Enumerated(EnumType.STRING)
  private FinancialEntityType financialEntityType;

  @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL, orphanRemoval = true)
  List<SubProjectBankEntity> subProjectBanks;

  /**
   * Converts this entity to a domain Bank object.
   * Copies properties from the entity to the domain model.
   *
   * @return the corresponding Bank domain object
   */
  public Bank toBank() {
    Bank bank = new Bank();
    BeanUtils.copyProperties(this, bank);
    return bank;
  }
}
