package es.miw.tfm.invierte.core.infrastructure.data.dao;

import es.miw.tfm.invierte.core.infrastructure.data.entity.SubProjectPropertyGroupEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing SubProjectPropertyGroupEntity objects.
 * <p>
 * Extends JpaRepository to provide CRUD operations and custom query
 * methods for SubProjectPropertyGroupEntity.
 * </p>
 *
 * @author denilssonmn
 * @see SubProjectPropertyGroupEntity
 */
public interface SubProjectPropertyGroupRepository
    extends JpaRepository<SubProjectPropertyGroupEntity, Integer> {

  List<SubProjectPropertyGroupEntity> findAllBySubProject_Id(Integer subProjectId);

  List<SubProjectPropertyGroupEntity> findAllByPropertyGroup_Id(Integer propertyGroupId);

}
