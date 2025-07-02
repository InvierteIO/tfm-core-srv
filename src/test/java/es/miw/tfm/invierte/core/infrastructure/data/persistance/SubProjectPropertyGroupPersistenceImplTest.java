package es.miw.tfm.invierte.core.infrastructure.data.persistance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import es.miw.tfm.invierte.core.domain.model.ProjectStage;
import es.miw.tfm.invierte.core.domain.model.PropertyGroup;
import es.miw.tfm.invierte.core.domain.model.SubProjectPropertyGroup;
import es.miw.tfm.invierte.core.infrastructure.data.dao.PropertyGroupRepository;
import es.miw.tfm.invierte.core.infrastructure.data.dao.SubProjectPropertyGroupRepository;
import es.miw.tfm.invierte.core.infrastructure.data.dao.SubProjectRepository;
import es.miw.tfm.invierte.core.infrastructure.data.entity.PropertyGroupEntity;
import es.miw.tfm.invierte.core.infrastructure.data.entity.SubProjectEntity;
import es.miw.tfm.invierte.core.infrastructure.data.entity.SubProjectPropertyGroupEntity;
import es.miw.tfm.invierte.core.infrastructure.data.persistence.SubProjectPropertyGroupPersistenceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class SubProjectPropertyGroupPersistenceImplTest {

  private SubProjectPropertyGroupRepository subProjectPropertyGroupRepository;
  private PropertyGroupRepository propertyGroupRepository;
  private SubProjectRepository subProjectRepository;
  private SubProjectPropertyGroupPersistenceImpl persistence;

  @BeforeEach
  void setUp() {
    subProjectPropertyGroupRepository = mock(SubProjectPropertyGroupRepository.class);
    propertyGroupRepository = mock(PropertyGroupRepository.class);
    subProjectRepository = mock(SubProjectRepository.class);
    persistence = new SubProjectPropertyGroupPersistenceImpl(
        subProjectPropertyGroupRepository,
        propertyGroupRepository,
        subProjectRepository
    );
  }

  @Test
  void create_shouldSave_whenEntitiesExist() {
    SubProjectPropertyGroup domain = mock(SubProjectPropertyGroup.class);
    ProjectStage stage = mock(ProjectStage.class);
    PropertyGroup group = mock(PropertyGroup.class);
    SubProjectEntity subProjectEntity = mock(SubProjectEntity.class);
    PropertyGroupEntity propertyGroupEntity = mock(PropertyGroupEntity.class);
    SubProjectPropertyGroupEntity savedEntity = mock(SubProjectPropertyGroupEntity.class);
    SubProjectPropertyGroup expected = new SubProjectPropertyGroup();

    when(domain.getStage()).thenReturn(stage);
    when(domain.getPropertyGroup()).thenReturn(group);
    when(stage.getId()).thenReturn(1);
    when(group.getId()).thenReturn(2);
    when(subProjectRepository.findById(1)).thenReturn(Optional.of(subProjectEntity));
    when(propertyGroupRepository.findById(2)).thenReturn(Optional.of(propertyGroupEntity));
    when(subProjectPropertyGroupRepository.save(any(SubProjectPropertyGroupEntity.class))).thenReturn(savedEntity);
    when(savedEntity.toSubProjectPropertyGroup()).thenReturn(expected);

    Mono<SubProjectPropertyGroup> result = persistence.create(domain);

    StepVerifier.create(result)
        .expectNext(expected)
        .verifyComplete();

    verify(subProjectRepository).findById(1);
    verify(propertyGroupRepository).findById(2);
    verify(subProjectPropertyGroupRepository).save(any(SubProjectPropertyGroupEntity.class));
    verify(savedEntity).toSubProjectPropertyGroup();
  }

  @Test
  void findAllBySubProjectId_shouldReturnList() {
    int subProjectId = 5;
    SubProjectPropertyGroupEntity entity1 = mock(SubProjectPropertyGroupEntity.class);
    SubProjectPropertyGroupEntity entity2 = mock(SubProjectPropertyGroupEntity.class);
    SubProjectPropertyGroup group1 = new SubProjectPropertyGroup();
    SubProjectPropertyGroup group2 = new SubProjectPropertyGroup();

    when(subProjectPropertyGroupRepository.findAllBySubProject_Id(subProjectId)).thenReturn(List.of(entity1, entity2));
    when(entity1.toSubProjectPropertyGroup()).thenReturn(group1);
    when(entity2.toSubProjectPropertyGroup()).thenReturn(group2);

    List<SubProjectPropertyGroup> result = persistence.findAllBySubProjectId(subProjectId);

    assertEquals(2, result.size());
    assertTrue(result.contains(group1));
    assertTrue(result.contains(group2));
    verify(subProjectPropertyGroupRepository).findAllBySubProject_Id(subProjectId);
    verify(entity1).toSubProjectPropertyGroup();
    verify(entity2).toSubProjectPropertyGroup();
  }

  @Test
  void deleteById_shouldDeleteAndNotDeletePropertyGroup_whenOtherRelationshipExists() {
    int id = 10;
    SubProjectPropertyGroupEntity entity = mock(SubProjectPropertyGroupEntity.class);
    PropertyGroupEntity propertyGroupEntity = mock(PropertyGroupEntity.class);
    SubProjectPropertyGroupEntity otherEntity = mock(SubProjectPropertyGroupEntity.class);

    when(subProjectPropertyGroupRepository.findById(id)).thenReturn(Optional.of(entity));
    when(entity.getPropertyGroup()).thenReturn(propertyGroupEntity);
    when(propertyGroupEntity.getId()).thenReturn(20);
    when(subProjectPropertyGroupRepository.findAllByPropertyGroup_Id(20)).thenReturn(List.of(entity, otherEntity));
    when(entity.getId()).thenReturn(id);
    when(otherEntity.getId()).thenReturn(99);

    Mono<Void> result = persistence.deleteById(id);

    StepVerifier.create(result)
        .verifyComplete();

    verify(subProjectPropertyGroupRepository).findById(id);
    verify(subProjectPropertyGroupRepository).delete(entity);
    verify(propertyGroupRepository, never()).deleteById(20);
  }

  @Test
  void deleteById_shouldDoNothing_whenNotExists() {
    int id = 10;
    when(subProjectPropertyGroupRepository.findById(id)).thenReturn(Optional.empty());

    Mono<Void> result = persistence.deleteById(id);

    StepVerifier.create(result)
        .verifyComplete();

    verify(subProjectPropertyGroupRepository).findById(id);
    verify(subProjectPropertyGroupRepository, never()).delete(any());
    verify(propertyGroupRepository, never()).deleteById(anyInt());
  }

  @Test
  void deleteAllByPropertyGroup_shouldDeleteAllAndPropertyGroup() {
    int propertyGroupId = 30;
    SubProjectPropertyGroupEntity entity1 = mock(SubProjectPropertyGroupEntity.class);
    SubProjectPropertyGroupEntity entity2 = mock(SubProjectPropertyGroupEntity.class);

    when(subProjectPropertyGroupRepository.findAllByPropertyGroup_Id(propertyGroupId)).thenReturn(List.of(entity1, entity2));
    when(entity1.getId()).thenReturn(1);
    when(entity2.getId()).thenReturn(2);

    Mono<Void> result = persistence.deleteAllByPropertyGroup(propertyGroupId);

    StepVerifier.create(result)
        .verifyComplete();

    verify(subProjectPropertyGroupRepository).findAllByPropertyGroup_Id(propertyGroupId);
    verify(subProjectPropertyGroupRepository).deleteById(1);
    verify(subProjectPropertyGroupRepository).deleteById(2);
    verify(propertyGroupRepository).deleteById(propertyGroupId);
  }
}