package es.miw.tfm.invierte.core.infrastructure.data.dao;

import es.miw.tfm.invierte.core.infrastructure.data.entity.RealEstateCompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing RealEstateCompanyEntity objects.
 * This interface extends JpaRepository to provide CRUD operations and custom query methods
 * for the RealEstateCompanyEntity.
 *
 * <p>The repository is responsible for interacting with the database to perform operations
 * related to real estate companies.
 *
 * @see RealEstateCompanyEntity
 *
 * @author denilssonmn
 */
public interface RealEstateCompanyRepository extends
    JpaRepository<RealEstateCompanyEntity, Integer> {

  RealEstateCompanyEntity findByTaxIdentificationNumber(String taxIdentificationNumber);

}
