package es.miw.tfm.invierte.core.domain.service;

import es.miw.tfm.invierte.core.domain.model.RealStateCompany;
import es.miw.tfm.invierte.core.domain.model.dto.RealStateCompanyDto;
import es.miw.tfm.invierte.core.domain.persistence.RealStateCompanyPersistence;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RealStateCompanyService {

  private final RealStateCompanyPersistence realStateCompanyPersistence;

  public Mono<RealStateCompany> create(RealStateCompany realStateCompany) {
    return this.realStateCompanyPersistence.create(realStateCompany);
  }

  public Mono<RealStateCompany> update(String taxIdentificationNumber, RealStateCompany realStateCompany) {
    return this.realStateCompanyPersistence.update(taxIdentificationNumber, realStateCompany);
  }

  public Mono<RealStateCompanyDto> readForProfile(String taxIdentificationNumber) {
    return this.realStateCompanyPersistence.readByTaxIdentificationNumber(taxIdentificationNumber)
      .map(realStateCompany -> {
        RealStateCompanyDto dto = new RealStateCompanyDto();
        BeanUtils.copyProperties(realStateCompany, dto);
        return dto;
      });
  }

  public Mono<RealStateCompany> read(String taxIdentificationNumber) {
    return this.realStateCompanyPersistence.readByTaxIdentificationNumber(taxIdentificationNumber);
  }

}
