package es.miw.tfm.invierte.core.infrastructure.data.persistance;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import es.miw.tfm.invierte.core.domain.exception.NotFoundException;
import es.miw.tfm.invierte.core.domain.model.PropertyGroup;
import es.miw.tfm.invierte.core.domain.model.PropertyGroupDocument;
import es.miw.tfm.invierte.core.infrastructure.data.dao.CatalogDetailRepository;
import es.miw.tfm.invierte.core.infrastructure.data.dao.PropertyGroupDocumentRepository;
import es.miw.tfm.invierte.core.infrastructure.data.dao.PropertyGroupRepository;
import es.miw.tfm.invierte.core.infrastructure.data.dao.SubProjectPropertyGroupRepository;
import es.miw.tfm.invierte.core.infrastructure.data.entity.CatalogDetailEntity;
import es.miw.tfm.invierte.core.infrastructure.data.entity.PropertyGroupDocumentEntity;
import es.miw.tfm.invierte.core.infrastructure.data.entity.PropertyGroupEntity;
import es.miw.tfm.invierte.core.infrastructure.data.entity.SubProjectPropertyGroupEntity;
import es.miw.tfm.invierte.core.infrastructure.data.persistence.PropertyGroupPersistenceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class PropertyGroupPersistenceImplTest {

  private PropertyGroupRepository propertyGroupRepository;
  private CatalogDetailRepository catalogDetailRepository;
  private PropertyGroupDocumentRepository propertyGroupDocumentRepository;
  private SubProjectPropertyGroupRepository subProjectPropertyGroupRepository;
  private PropertyGroupPersistenceImpl propertyGroupPersistence;

  @BeforeEach
  void setUp() {
    propertyGroupRepository = mock(PropertyGroupRepository.class);
    catalogDetailRepository = mock(CatalogDetailRepository.class);
    propertyGroupDocumentRepository = mock(PropertyGroupDocumentRepository.class);
    subProjectPropertyGroupRepository = mock(SubProjectPropertyGroupRepository.class);
    propertyGroupPersistence = new PropertyGroupPersistenceImpl(
        propertyGroupRepository,
        catalogDetailRepository,
        propertyGroupDocumentRepository,
        subProjectPropertyGroupRepository
    );
  }

  @Test
  void create_shouldSavePropertyGroup() {
    PropertyGroup propertyGroup = mock(PropertyGroup.class);
    PropertyGroupEntity entity = mock(PropertyGroupEntity.class);
    PropertyGroup expected = new PropertyGroup();

    when(propertyGroupRepository.save(any(PropertyGroupEntity.class))).thenReturn(entity);
    when(entity.toPropertyGroup()).thenReturn(expected);

    Mono<PropertyGroup> result = propertyGroupPersistence.create(propertyGroup);

    StepVerifier.create(result)
        .expectNext(expected)
        .verifyComplete();

    verify(propertyGroupRepository).save(any(PropertyGroupEntity.class));
    verify(entity).toPropertyGroup();
  }

  @Test
  void update_shouldUpdate_whenExists() {
    Integer id = 1;
    PropertyGroup propertyGroup = mock(PropertyGroup.class);
    PropertyGroupEntity existing = mock(PropertyGroupEntity.class);
    PropertyGroupEntity updated = mock(PropertyGroupEntity.class);
    PropertyGroup expected = new PropertyGroup();

    when(propertyGroupRepository.findById(id)).thenReturn(Optional.of(existing));
    when(existing.getId()).thenReturn(id);
    when(propertyGroupRepository.save(any(PropertyGroupEntity.class))).thenReturn(updated);
    when(updated.toPropertyGroup()).thenReturn(expected);

    Mono<PropertyGroup> result = propertyGroupPersistence.update(id, propertyGroup);

    StepVerifier.create(result)
        .expectNext(expected)
        .verifyComplete();

    verify(propertyGroupRepository).findById(id);
    verify(propertyGroupRepository).save(any(PropertyGroupEntity.class));
    verify(updated).toPropertyGroup();
  }

  @Test
  void update_shouldThrowNotFound_whenNotExists() {
    Integer id = 1;
    PropertyGroup propertyGroup = mock(PropertyGroup.class);

    when(propertyGroupRepository.findById(id)).thenReturn(Optional.empty());

    Mono<PropertyGroup> result = propertyGroupPersistence.update(id, propertyGroup);

    StepVerifier.create(result)
        .expectError(NotFoundException.class)
        .verify();

    verify(propertyGroupRepository).findById(id);
  }

  @Test
  void readById_shouldReturn_whenExists() {
    Integer id = 1;
    PropertyGroupEntity entity = mock(PropertyGroupEntity.class);
    PropertyGroup expected = new PropertyGroup();

    when(propertyGroupRepository.findById(id)).thenReturn(Optional.of(entity));
    when(entity.toPropertyGroup()).thenReturn(expected);

    Mono<PropertyGroup> result = propertyGroupPersistence.readById(id);

    StepVerifier.create(result)
        .expectNext(expected)
        .verifyComplete();

    verify(propertyGroupRepository).findById(id);
    verify(entity).toPropertyGroup();
  }

  @Test
  void createDocument_shouldSave_whenPropertyGroupExists() {
    Integer groupId = 1;
    PropertyGroupDocument doc = mock(PropertyGroupDocument.class);
    SubProjectPropertyGroupEntity subProjectEntity = mock(SubProjectPropertyGroupEntity.class);
    PropertyGroupDocumentEntity documentEntity = mock(PropertyGroupDocumentEntity.class);
    PropertyGroupDocument expected = new PropertyGroupDocument();

    when(subProjectPropertyGroupRepository.findById(groupId)).thenReturn(Optional.of(subProjectEntity));
    when(doc.getCatalogDetail()).thenReturn(null);
    when(catalogDetailRepository.findByCode(null)).thenReturn(Optional.ofNullable(null));
    when(propertyGroupDocumentRepository.save(any(PropertyGroupDocumentEntity.class))).thenReturn(documentEntity);
    when(documentEntity.toPropertyGroupDocument()).thenReturn(expected);

    Mono<PropertyGroupDocument> result = propertyGroupPersistence.createDocument(groupId, doc);

    StepVerifier.create(result)
        .expectNext(expected)
        .verifyComplete();

    verify(subProjectPropertyGroupRepository).findById(groupId);
    verify(propertyGroupDocumentRepository).save(any(PropertyGroupDocumentEntity.class));
    verify(documentEntity).toPropertyGroupDocument();
  }

  @Test
  void createDocument_shouldThrowNotFound_whenPropertyGroupNotExists() {
    Integer groupId = 1;
    PropertyGroupDocument doc = mock(PropertyGroupDocument.class);

    when(subProjectPropertyGroupRepository.findById(groupId)).thenReturn(Optional.empty());

    Mono<PropertyGroupDocument> result = propertyGroupPersistence.createDocument(groupId, doc);

    StepVerifier.create(result)
        .expectError(NotFoundException.class)
        .verify();

    verify(subProjectPropertyGroupRepository).findById(groupId);
  }

  @Test
  void deleteDocument_shouldDelete_whenExists() {
    Integer docId = 1;
    PropertyGroupDocumentEntity entity = mock(PropertyGroupDocumentEntity.class);

    when(propertyGroupDocumentRepository.findById(docId)).thenReturn(Optional.of(entity));
    when(propertyGroupDocumentRepository.save(entity)).thenReturn(entity);
    doNothing().when(propertyGroupDocumentRepository).delete(entity);

    Mono<Void> result = propertyGroupPersistence.deleteDocument(docId);

    StepVerifier.create(result)
        .verifyComplete();

    verify(propertyGroupDocumentRepository).findById(docId);
    verify(propertyGroupDocumentRepository).save(entity);
    verify(propertyGroupDocumentRepository).delete(entity);
  }

  @Test
  void deleteDocument_shouldDoNothing_whenNotExists() {
    Integer docId = 1;
    when(propertyGroupDocumentRepository.findById(docId)).thenReturn(Optional.empty());

    Mono<Void> result = propertyGroupPersistence.deleteDocument(docId);

    StepVerifier.create(result)
        .verifyComplete();

    verify(propertyGroupDocumentRepository).findById(docId);
  }
}