package es.miw.tfm.invierte.core.domain.persistence;

import es.miw.tfm.invierte.core.domain.model.RealStateCompany;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * Interface for RealStateCompany persistence operations.
 * Provides methods for creating, updating, and retrieving RealStateCompany entities.
 * This interface is implemented by classes that handle data access for RealStateCompanies.
 *
 * @author denilssonmn
 * @author devcastlecix
 */
@Repository
public interface RealStateCompanyPersistence {

  Mono<RealStateCompany> create(RealStateCompany realStateCompany);

  Mono<RealStateCompany> readByTaxIdentificationNumber(String taxIdentificationNumber);

  Mono<RealStateCompany> update(String taxIdentificationNumber, RealStateCompany realStateCompany);

}
