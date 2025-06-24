package es.miw.tfm.invierte.core.infrastructure.data.entity;

import es.miw.tfm.invierte.core.domain.model.StageBank;
import es.miw.tfm.invierte.core.domain.model.enums.Currency;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * JPA entity representing the association between a subproject and a bank account.
 * Maps the domain model to the database table `subproject_bank`.
 * Stores information about the account number, interbank account number, currency,
 * and related subproject and bank.
 *
 * @author denilssonmn
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subproject_bank")
public class SubProjectBankEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "account_number", length = 18, nullable = false)
  private String accountNumber;

  @Column(name = "interbank_account_number", length = 20, nullable = false)
  private String interbankAccountNumber;

  @Column(length = 3)
  @Enumerated(EnumType.STRING)
  private Currency currency;

  @ManyToOne
  @JoinColumn(name = "subproject_id", referencedColumnName = "id")
  private SubProjectEntity subProject;

  @ManyToOne
  @JoinColumn(name = "bank_id", referencedColumnName = "id")
  private BankEntity bank;

  /**
   * Constructs a SubProjectBankEntity from a domain StageBank object and its parent
   * SubProjectEntity.
   * Copies properties from the domain model and initializes the related bank entity
   * from the corresponding domain bank.
   *
   * @param stageBank the domain StageBank to copy properties from
   * @param subProjectEntity the parent SubProjectEntity to associate with this subproject bank
   */
  public SubProjectBankEntity(StageBank stageBank,
      SubProjectEntity subProjectEntity) {
    BeanUtils.copyProperties(stageBank, this);
    this.subProject = subProjectEntity;

    BankEntity bankEntity = new BankEntity();
    BeanUtils.copyProperties(stageBank.getBank(), bankEntity);
    this.bank = bankEntity;
  }

  /**
   * Converts this entity to a domain StageBank object.
   * Copies properties from the entity to the domain model and sets the related
   * bank.
   *
   * @return the corresponding StageBank domain object
   */
  public StageBank toStageBank() {
    StageBank stageBank = new StageBank();
    BeanUtils.copyProperties(this, stageBank);
    stageBank.setBank(this.bank.toBank());
    return stageBank;
  }
}
