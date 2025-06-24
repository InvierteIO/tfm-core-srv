package es.miw.tfm.invierte.core.infrastructure.data.entity;

import es.miw.tfm.invierte.core.domain.model.StageBonusType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
 * JPA entity representing the association between a subproject and a financial bonus type.
 * Maps the domain model to the database table `subproject_bonus_type`.
 * Stores information about the bonus type value and its relation to subprojects
 * and financial bonus types.
 *
 * @author denilssonmn
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subproject_bonus_type")
public class SubProjectBonusTypeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "type_value", length = 500, nullable = false)
  private String typeValue;

  @ManyToOne
  @JoinColumn(name = "subproject_id", referencedColumnName = "id")
  private SubProjectEntity subProject;

  @ManyToOne
  @JoinColumn(name = "financial_bonus_type_id", referencedColumnName = "id")
  private FinancialBonusTypeEntity financialBonusType;

  /**
   * Constructs a SubProjectBonusTypeEntity from a domain StageBonusType object and its parent
   * SubProjectEntity.
   * Copies properties from the domain model and associates this entity with the given subproject.
   * Initializes the related financial bonus type entity from the corresponding domain object.
   *
   * @param stageBonusType the domain StageBonusType to copy properties from
   * @param subProjectEntity SubProjectEntity to associate with this subproject bonus type
   */
  public SubProjectBonusTypeEntity(StageBonusType stageBonusType,
      SubProjectEntity subProjectEntity) {
    BeanUtils.copyProperties(stageBonusType, this);
    this.subProject = subProjectEntity;

    FinancialBonusTypeEntity financialBonusTypeEntity = new FinancialBonusTypeEntity();
    BeanUtils.copyProperties(stageBonusType.getFinancialBonusType(), financialBonusTypeEntity);
    this.financialBonusType = financialBonusTypeEntity;
  }

  /**
   * Converts this entity to a domain StageBonusType object.
   * Copies properties from the entity to the domain model and sets the related
   * financial bonus type.
   *
   * @return the corresponding StageBonusType domain object
   */
  public StageBonusType toStageBonusTypes() {
    StageBonusType bonusType = new StageBonusType();
    BeanUtils.copyProperties(this, bonusType);
    bonusType.setFinancialBonusType(this.financialBonusType.toFinancialBonusType());
    return bonusType;
  }
}
