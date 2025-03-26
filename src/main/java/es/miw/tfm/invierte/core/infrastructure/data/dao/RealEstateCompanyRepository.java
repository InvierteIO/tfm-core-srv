package es.miw.tfm.invierte.core.infrastructure.data.dao;

import es.miw.tfm.invierte.core.infrastructure.data.entity.RealEstateCompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RealEstateCompanyRepository extends JpaRepository<RealEstateCompanyEntity, Integer> {

  RealEstateCompanyEntity findByTaxIdentificationNumber(String taxIdentificationNumber);

}
