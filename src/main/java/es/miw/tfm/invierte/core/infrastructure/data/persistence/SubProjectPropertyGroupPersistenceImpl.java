package es.miw.tfm.invierte.core.infrastructure.data.persistence;

import es.miw.tfm.invierte.core.domain.exception.NotFoundException;
import es.miw.tfm.invierte.core.domain.model.SubProjectPropertyGroup;
import es.miw.tfm.invierte.core.domain.persistence.SubProjectPropertyGroupPersistence;
import es.miw.tfm.invierte.core.infrastructure.data.dao.PropertyGroupRepository;
import es.miw.tfm.invierte.core.infrastructure.data.dao.SubProjectPropertyGroupRepository;
import es.miw.tfm.invierte.core.infrastructure.data.dao.SubProjectRepository;
import es.miw.tfm.invierte.core.infrastructure.data.entity.PropertyGroupEntity;
import es.miw.tfm.invierte.core.infrastructure.data.entity.SubProjectEntity;
import es.miw.tfm.invierte.core.infrastructure.data.entity.SubProjectPropertyGroupEntity;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

/**
 * Implementation of the {@link SubProjectPropertyGroupPersistence} interface.
 * Handles persistence operations for subproject-property group associations using
 * the {@link SubProjectPropertyGroupRepository}.
 * Maps between domain models and entity representations.
 *
 * @author denilssonmn
 */

@Repository
@RequiredArgsConstructor
@Slf4j
public class SubProjectPropertyGroupPersistenceImpl implements SubProjectPropertyGroupPersistence {

  private final SubProjectPropertyGroupRepository subProjectPropertyGroupRepository;
  
  private final PropertyGroupRepository propertyGroupRepository;
  
  private final SubProjectRepository subProjectRepository;

  @Override
  @Transactional
  public Mono<SubProjectPropertyGroup> create(SubProjectPropertyGroup subProjectPropertyGroup) {
    final var propertyGroupEntity = getPropertyGroupEntity(subProjectPropertyGroup);
    final var subProjectEntity = getSubProjectEntity(subProjectPropertyGroup);

    final var subProjectPropertyGroupEntity =
        new SubProjectPropertyGroupEntity(subProjectEntity, propertyGroupEntity);

    return Mono.just(this.subProjectPropertyGroupRepository.save(subProjectPropertyGroupEntity)
        .toSubProjectPropertyGroup());
  }

  @Override
  public List<SubProjectPropertyGroup> findAllBySubProjectId(Integer subProjectId) {
    return this.subProjectPropertyGroupRepository.findAllBySubProject_Id(subProjectId)
        .stream()
        .map(SubProjectPropertyGroupEntity::toSubProjectPropertyGroup)
        .toList();
  }

  @Override
  @Transactional
  public Mono<Void> deleteById(Integer stagePropertyGroupId) {
    final Optional<SubProjectPropertyGroupEntity> subProjectPropertyGroupEntityOpt =
        this.subProjectPropertyGroupRepository.findById(stagePropertyGroupId);

    subProjectPropertyGroupEntityOpt
        .ifPresent(subProjectPropertyGroupEntity -> {
          final var existsOtherPropertyGroupRelationShip =
              this.existsOtherPropertyGroupRelationShip(stagePropertyGroupId,
                  subProjectPropertyGroupEntity);
          this.subProjectPropertyGroupRepository.delete(subProjectPropertyGroupEntity);
          if (!existsOtherPropertyGroupRelationShip) {
            this.propertyGroupRepository.deleteById(subProjectPropertyGroupEntity.getId());
          }
        });

    return Mono.empty();
  }

  @Override
  @Transactional
  public Mono<Void> deleteAllByPropertyGroup(Integer propertyGroupId) {
    this.subProjectPropertyGroupRepository.findAllByPropertyGroup_Id(propertyGroupId)
        .forEach(subProjectPropertyGroupEntity ->
            this.subProjectPropertyGroupRepository
                .deleteById(subProjectPropertyGroupEntity.getId()));
    this.propertyGroupRepository.deleteById(propertyGroupId);
    return Mono.empty();
  }

  private boolean existsOtherPropertyGroupRelationShip(Integer stagePropertyGroupId,
      SubProjectPropertyGroupEntity subProjectPropertyGroupEntity) {
    return Optional.of(subProjectPropertyGroupEntity)
        .stream()
        .map(SubProjectPropertyGroupEntity::getPropertyGroup)
        .flatMap(propertyGroupEntity -> this.subProjectPropertyGroupRepository
            .findAllByPropertyGroup_Id(propertyGroupEntity.getId()).stream())
        .anyMatch(otherSubProjectPropertyGroupEntity ->
            !otherSubProjectPropertyGroupEntity.getId().equals(stagePropertyGroupId));
  }

  private SubProjectEntity getSubProjectEntity(SubProjectPropertyGroup subProjectPropertyGroup) {
    return this.subProjectRepository.findById(subProjectPropertyGroup.getStage().getId())
        .orElseThrow(
            () -> new NotFoundException("Non existent SubProject with id: "
                + subProjectPropertyGroup.getStage().getId()));
  }

  private PropertyGroupEntity getPropertyGroupEntity(
      SubProjectPropertyGroup subProjectPropertyGroup) {
    return this.propertyGroupRepository.findById(subProjectPropertyGroup.getPropertyGroup().getId())
        .orElseThrow(
            () -> new NotFoundException("Non existent PropertyGroup with id: "
                + subProjectPropertyGroup.getPropertyGroup().getId()));
  }

}
