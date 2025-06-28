package es.miw.tfm.invierte.core.domain.service;

import es.miw.tfm.invierte.core.domain.model.RealStateCompany;
import es.miw.tfm.invierte.core.domain.model.dto.RealStateCompanyDto;
import es.miw.tfm.invierte.core.domain.persistence.RealStateCompanyPersistence;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Service class for RealStateCompany operations.
 * Provides methods for creating, updating, and retrieving RealStateCompany entities.
 * This class acts as a bridge between the controller and persistence layers.
 *
 * @author denilssonmn
 * @author devcastlecix
 */

@Service
@RequiredArgsConstructor
public class RealStateCompanyService {

  private final RealStateCompanyPersistence realStateCompanyPersistence;

  /**
   * Creates a new RealStateCompany entity.
   *
   * @param realStateCompany the RealStateCompany entity to create
   * @return a Mono containing the created RealStateCompany
   */
  public Mono<RealStateCompany> create(RealStateCompany realStateCompany) {
    return this.realStateCompanyPersistence.create(realStateCompany);
  }

  /**
   * Updates an existing RealStateCompany entity.
   *
   * @param taxIdentificationNumber the tax identification number of the RealStateCompany to update
   * @param realStateCompany the updated RealStateCompany entity
   * @return a Mono containing the updated RealStateCompany
   */
  public Mono<RealStateCompany> update(String taxIdentificationNumber,
      RealStateCompany realStateCompany) {
    return this.realStateCompanyPersistence.update(taxIdentificationNumber, realStateCompany);
  }

  /**
   * Reads a RealStateCompany entity and maps it to a RealStateCompanyDto for profile purposes.
   *
   * @param taxIdentificationNumber the tax identification number of the RealStateCompany to read
   * @return a Mono containing the RealStateCompanyDto
   */
  public Mono<RealStateCompanyDto> readForProfile(String taxIdentificationNumber) {
    return this.realStateCompanyPersistence.readByTaxIdentificationNumber(taxIdentificationNumber)
      .map(realStateCompany -> {
        RealStateCompanyDto dto = new RealStateCompanyDto();
        BeanUtils.copyProperties(realStateCompany, dto);
        return dto;
      });
  }

  /**
   * Reads a RealStateCompany entity by its tax identification number.
   *
   * @param taxIdentificationNumber the tax identification number of the RealStateCompany to read
   * @return a Mono containing the RealStateCompany
   */
  public Mono<RealStateCompany> read(String taxIdentificationNumber) {
    return this.realStateCompanyPersistence.readByTaxIdentificationNumber(taxIdentificationNumber);
  }

}
