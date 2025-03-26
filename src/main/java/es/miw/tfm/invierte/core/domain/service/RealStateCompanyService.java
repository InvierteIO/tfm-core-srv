package es.miw.tfm.invierte.core.domain.service;

import es.miw.tfm.invierte.core.domain.model.RealStateCompany;
import es.miw.tfm.invierte.core.domain.persistence.RealStateCompanyPersistence;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RealStateCompanyService {

  private final RealStateCompanyPersistence realStateCompanyPersistence;

  public Mono<RealStateCompany> create(RealStateCompany realStateCompany) {
    return this.realStateCompanyPersistence.create(realStateCompany);
  }

  public Mono<RealStateCompany> read(String taxIdentificationNumber) {
    return this.realStateCompanyPersistence.readByTaxIdentificationNumber(taxIdentificationNumber);
  }

  public Mono<RealStateCompany> update(String taxIdentificationNumber, RealStateCompany realStateCompany) {
    return this.realStateCompanyPersistence.update(taxIdentificationNumber, realStateCompany);
  }

}
