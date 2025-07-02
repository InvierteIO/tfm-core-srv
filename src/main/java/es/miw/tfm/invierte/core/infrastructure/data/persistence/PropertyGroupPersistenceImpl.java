package es.miw.tfm.invierte.core.infrastructure.data.persistence;

import es.miw.tfm.invierte.core.domain.exception.NotFoundException;
import es.miw.tfm.invierte.core.domain.model.ProjectDocument;
import es.miw.tfm.invierte.core.domain.model.PropertyGroup;
import es.miw.tfm.invierte.core.domain.model.PropertyGroupDocument;
import es.miw.tfm.invierte.core.domain.persistence.PropertyGroupPersistence;
import es.miw.tfm.invierte.core.infrastructure.data.dao.CatalogDetailRepository;
import es.miw.tfm.invierte.core.infrastructure.data.dao.PropertyGroupDocumentRepository;
import es.miw.tfm.invierte.core.infrastructure.data.dao.PropertyGroupRepository;
import es.miw.tfm.invierte.core.infrastructure.data.dao.SubProjectPropertyGroupRepository;
import es.miw.tfm.invierte.core.infrastructure.data.entity.ProjectDocumentEntity;
import es.miw.tfm.invierte.core.infrastructure.data.entity.PropertyGroupDocumentEntity;
import es.miw.tfm.invierte.core.infrastructure.data.entity.PropertyGroupEntity;

import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

/**
 * Implementation of the {@link PropertyGroupPersistence} interface.
 * Handles persistence operations for property groups using the
 * {@link PropertyGroupRepository}. Maps between domain models and
 * entity representations, providing create, update, and read operations.
 *
 * @author denilssonmn
 */

@Repository
@RequiredArgsConstructor
@Slf4j
public class PropertyGroupPersistenceImpl implements PropertyGroupPersistence {

  private final PropertyGroupRepository propertyGroupRepository;

  private final CatalogDetailRepository catalogDetailRepository;

  private final PropertyGroupDocumentRepository propertyGroupDocumentRepository;

  private final SubProjectPropertyGroupRepository subProjectPropertyGroupRepository;

  @Override
  public Mono<PropertyGroup> create(PropertyGroup propertyGroup) {
    final var propertyGroupEntity = new PropertyGroupEntity(propertyGroup);
    return Mono.just(this.propertyGroupRepository.save(propertyGroupEntity)
        .toPropertyGroup());
  }

  @Override
  public Mono<PropertyGroup> update(Integer id, PropertyGroup propertyGroup) {
    return Mono.just(this.propertyGroupRepository.findById(id))
        .switchIfEmpty(Mono.error(
            new NotFoundException("Non existent PropertyGroup with id: " + id)))
        .flatMap(existingEntity -> {
          if (existingEntity.isEmpty()) {
            return Mono.error(new NotFoundException("Non existent PropertyGroup with id: " + id));
          }
          PropertyGroupEntity propertyGroupEntityDb = new PropertyGroupEntity(propertyGroup);
          propertyGroupEntityDb.setId(existingEntity.get().getId());
          return Mono.just(this.propertyGroupRepository.save(propertyGroupEntityDb));
        })
        .map(PropertyGroupEntity::toPropertyGroup);
  }

  @Override
  @Transactional(readOnly = true)
  public Mono<PropertyGroup> readById(Integer id) {
    return Mono.just(this.propertyGroupRepository.findById(id))
        .switchIfEmpty(Mono.error(
            new NotFoundException("Non existent PropertyGroup with id: " + id)))
        .map(Optional::get)
        .map(PropertyGroupEntity::toPropertyGroup);
  }

  @Override
  @Transactional
  public Mono<Void> deleteDocument(Integer documentId) {
    return Mono.fromRunnable(() -> {
      this.propertyGroupDocumentRepository.findById(documentId)
          .ifPresent(propertyGroupDocumentEntity -> {
            propertyGroupDocumentEntity.setSubProjectPropertyGroup(null);
            this.propertyGroupDocumentRepository.save(propertyGroupDocumentEntity);
            this.propertyGroupDocumentRepository.delete(propertyGroupDocumentEntity);
          });
    });
  }

  @Override
  @Transactional
  public Mono<PropertyGroupDocument> createDocument(Integer propertyGroupId,
      PropertyGroupDocument propertyGroupDocumentDto) {
    return Mono.just(this.subProjectPropertyGroupRepository.findById(propertyGroupId))
        .switchIfEmpty(Mono.error(new NotFoundException("Non existent PropertyGroup with id: "
            + propertyGroupId)))
        .flatMap(subProjectPropertyGroupEntity -> {
          if (subProjectPropertyGroupEntity.isEmpty()) {
            return Mono.error(new NotFoundException("Non existent PropertyGroup with id: "
                + propertyGroupId));
          }
          final var catalogDetailCode =
              Objects.nonNull(propertyGroupDocumentDto.getCatalogDetail())
              ? propertyGroupDocumentDto.getCatalogDetail().getCode() : null;

          final var catalogDetail = this.catalogDetailRepository.findByCode(catalogDetailCode)
              .orElse(null);

          PropertyGroupDocumentEntity propertyGroupDocumentEntity =
              new PropertyGroupDocumentEntity(propertyGroupDocumentDto,
                  subProjectPropertyGroupEntity.get(), catalogDetail);
          return Mono.just(this.propertyGroupDocumentRepository.save(propertyGroupDocumentEntity));
        })
        .map(PropertyGroupDocumentEntity::toPropertyGroupDocument);
  }
}
