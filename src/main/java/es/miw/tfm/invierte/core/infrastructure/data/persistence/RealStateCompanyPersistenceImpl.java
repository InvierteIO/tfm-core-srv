package es.miw.tfm.invierte.core.infrastructure.data.persistence;

import es.miw.tfm.invierte.core.domain.exception.ConflictException;
import es.miw.tfm.invierte.core.domain.exception.NotFoundException;
import es.miw.tfm.invierte.core.domain.model.RealStateCompany;
import es.miw.tfm.invierte.core.domain.persistence.RealStateCompanyPersistence;
import es.miw.tfm.invierte.core.infrastructure.data.dao.RealEstateCompanyRepository;
import es.miw.tfm.invierte.core.infrastructure.data.entity.RealEstateCompanyEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * Implementation of the RealStateCompanyPersistence interface.
 * This class provides methods for creating, reading, and updating RealStateCompany entities.
 * It interacts with the RealEstateCompanyRepository to perform database operations.
 *
 * <p>The class ensures that business rules are enforced, such as preventing duplicate
 * tax identification numbers and handling non-existent entities.
 *
 * <p>This class uses reactive programming with Project Reactor to handle asynchronous operations.
 *
 * @see RealStateCompanyPersistence
 * @see RealEstateCompanyRepository
 * @see RealEstateCompanyEntity
 *
 * @author denilssonmn
 */
@Repository
@RequiredArgsConstructor
public class RealStateCompanyPersistenceImpl implements RealStateCompanyPersistence {

  private final RealEstateCompanyRepository realEstateCompanyRepository;

  @Override
  public Mono<RealStateCompany> create(RealStateCompany realStateCompany) {
    return this.assertTaxIdentificationNumberNotExist(realStateCompany.getTaxIdentificationNumber())
        .then(Mono.just(this.realEstateCompanyRepository
            .save(new RealEstateCompanyEntity(realStateCompany))))
        .map(RealEstateCompanyEntity::toRealStateCompany);
  }

  @Override
  public Mono<RealStateCompany> readByTaxIdentificationNumber(String taxIdentificationNumber) {
    return Mono.just(this.realEstateCompanyRepository
            .findByTaxIdentificationNumber(taxIdentificationNumber))
        .switchIfEmpty(Mono.error(
            new NotFoundException("Non existent RealStateCompany with tax identification: "
                + taxIdentificationNumber)))
        .map(RealEstateCompanyEntity::toRealStateCompany);
  }

  @Override
  public Mono<RealStateCompany> update(String taxIdentificationNumber,
      RealStateCompany realStateCompany) {

    return Mono.just(this.realEstateCompanyRepository
            .findByTaxIdentificationNumber(taxIdentificationNumber))
        .switchIfEmpty(Mono.error(
            new NotFoundException("Non existent RealStateCompany with Tax identification: "
            + taxIdentificationNumber)))
        .map(realEstateCompanyEntity -> {
          realStateCompany.setTaxIdentificationNumber(taxIdentificationNumber);
          BeanUtils.copyProperties(realStateCompany, realEstateCompanyEntity);
          return this.realEstateCompanyRepository.save(realEstateCompanyEntity);
        })
        .map(RealEstateCompanyEntity::toRealStateCompany);
  }

  private Mono<Void> assertTaxIdentificationNumberNotExist(String taxIdentificationNumber) {
    return Mono.justOrEmpty(this.realEstateCompanyRepository
            .findByTaxIdentificationNumber(taxIdentificationNumber))
        .map(realStateCompany ->
            Mono.error(
                new ConflictException("Already Exists RealStateCompany with TaxIdentification : "
                + taxIdentificationNumber)))
        .then();
  }
}
