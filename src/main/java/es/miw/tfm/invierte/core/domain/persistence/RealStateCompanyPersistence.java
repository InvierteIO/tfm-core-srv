package es.miw.tfm.invierte.core.domain.persistence;

import es.miw.tfm.invierte.core.domain.model.RealStateCompany;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface RealStateCompanyPersistence {

  Mono<RealStateCompany> create(RealStateCompany realStateCompany);

  Mono<RealStateCompany> readByTaxIdentificationNumber(String taxIdentificationNumber);

  Mono<RealStateCompany> update(String taxIdentificationNumber, RealStateCompany realStateCompany);

}
