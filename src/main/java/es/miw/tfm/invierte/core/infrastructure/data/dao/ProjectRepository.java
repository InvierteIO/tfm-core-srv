package es.miw.tfm.invierte.core.infrastructure.data.dao;

import es.miw.tfm.invierte.core.infrastructure.data.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA repository interface for managing {@link ProjectEntity} entities.
 * Provides CRUD operations and custom queries for Project persistence.
 *
 * <p>Extends {@link org.springframework.data.jpa.repository.JpaRepository} to inherit standard
 * data access methods.
 *
 * @author denilssonmn
 */
public interface ProjectRepository extends JpaRepository<ProjectEntity, Integer> {

  ProjectEntity findByName(String name);

  ProjectEntity findByTaxIdentificationNumberAndId(String taxIdentificationNumber,
      Integer projectId);
}
