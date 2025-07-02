package es.miw.tfm.invierte.core.domain.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import es.miw.tfm.invierte.core.domain.model.Project;
import es.miw.tfm.invierte.core.domain.model.ProjectStage;
import es.miw.tfm.invierte.core.domain.model.PropertyGroup;
import es.miw.tfm.invierte.core.domain.model.PropertyGroupDocument;
import es.miw.tfm.invierte.core.domain.model.SubProjectPropertyGroup;
import es.miw.tfm.invierte.core.domain.persistence.FileUploadPersistence;
import es.miw.tfm.invierte.core.domain.persistence.ProjectPersistence;
import es.miw.tfm.invierte.core.domain.persistence.PropertyGroupPersistence;
import es.miw.tfm.invierte.core.domain.persistence.SubProjectPersistence;
import es.miw.tfm.invierte.core.domain.persistence.SubProjectPropertyGroupPersistence;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class PropertyGroupServiceTest {

  @Mock
  private ProjectPersistence projectPersistence;
  @Mock
  private SubProjectPersistence subProjectPersistence;
  @Mock
  private PropertyGroupPersistence propertyGroupPersistence;
  @Mock
  private SubProjectPropertyGroupPersistence subProjectPropertyGroupPersistence;
  @Mock
  private FileUploadPersistence fileUploadPersistence;
  @Mock
  private PropertyService propertyService;

  @InjectMocks
  private PropertyGroupService propertyGroupService;

  private static final Integer VALUE_ONE = 1;

  @Test
  void create_shouldCreateAndLinkPropertyGroups() {
    ProjectStage stage = new ProjectStage();
    stage.setId(1);
    PropertyGroup group = new PropertyGroup();
    SubProjectPropertyGroup spg = new SubProjectPropertyGroup();
    spg.setStage(stage);
    spg.setPropertyGroup(group);

    when(propertyGroupPersistence.create(any())).thenReturn(Mono.just(group));
    when(subProjectPersistence.readById(anyInt())).thenReturn(Mono.just(stage));
    when(subProjectPropertyGroupPersistence.create(any())).thenAnswer(inv -> Mono.just(inv.getArgument(0)));

    StepVerifier.create(propertyGroupService.create(List.of(spg)))
        .expectNextCount(1)
        .verifyComplete();

    verify(propertyGroupPersistence).create(any());
    verify(subProjectPersistence).readById(anyInt());
    verify(subProjectPropertyGroupPersistence).create(any());
  }

  @Test
  void readByTaxIdentificationNumberAndProjectId_shouldReturnSubProjectPropertyGroups() {
    String taxId = "TAX123";
    Integer projectId = 1;
    Integer stageId = 10;

    ProjectStage stage = new ProjectStage();
    stage.setId(stageId);

    Project project = new Project();
    project.setProjectStages(List.of(stage));

    SubProjectPropertyGroup spg = new SubProjectPropertyGroup();
    spg.setStage(stage);
    spg.setPropertyGroup(new PropertyGroup());

    when(projectPersistence.readByTaxIdentificationNumberAndId(taxId, projectId))
        .thenReturn(Mono.just(project));
    when(subProjectPropertyGroupPersistence.findAllBySubProjectId(stageId))
        .thenReturn(List.of(spg));

    StepVerifier.create(propertyGroupService.readByTaxIdentificationNumberAndProjectId(taxId, projectId))
        .expectNext(spg)
        .verifyComplete();

    verify(projectPersistence).readByTaxIdentificationNumberAndId(taxId, projectId);
    verify(subProjectPropertyGroupPersistence).findAllBySubProjectId(stageId);
  }

  @Test
  void delete_shouldDeleteSubProjectPropertyGroup() {
    when(projectPersistence.readByTaxIdentificationNumberAndId(anyString(), anyInt()))
        .thenReturn(Mono.just(new Project()));
    when(subProjectPropertyGroupPersistence.deleteById(anyInt()))
        .thenReturn(Mono.empty());

    StepVerifier.create(propertyGroupService.delete("TAX", 1, 2))
        .verifyComplete();

    verify(subProjectPropertyGroupPersistence).deleteById(2);
  }

  @Test
  void deleteAllByPropertyGroup_shouldDeleteAllAssociations() {
    when(projectPersistence.readByTaxIdentificationNumberAndId(anyString(), anyInt()))
        .thenReturn(Mono.just(new Project()));
    when(subProjectPropertyGroupPersistence.deleteAllByPropertyGroup(anyInt()))
        .thenReturn(Mono.empty());

    StepVerifier.create(propertyGroupService.deleteAllByPropertyGroup("TAX", 1, 3))
        .verifyComplete();

    verify(subProjectPropertyGroupPersistence).deleteAllByPropertyGroup(3);
  }

  @Test
  void assign_shouldAssignPropertyGroupsToStages() {
    ProjectStage stage = new ProjectStage();
    stage.setId(1);
    PropertyGroup group = new PropertyGroup();
    group.setId(2);
    SubProjectPropertyGroup spg = new SubProjectPropertyGroup();
    spg.setStage(stage);
    spg.setPropertyGroup(group);

    when(subProjectPersistence.readById(anyInt())).thenReturn(Mono.just(stage));
    when(propertyGroupPersistence.readById(anyInt())).thenReturn(Mono.just(group));
    when(subProjectPropertyGroupPersistence.create(any())).thenAnswer(inv -> Mono.just(inv.getArgument(0)));

    StepVerifier.create(propertyGroupService.assign(List.of(spg)))
        .expectNextCount(1)
        .verifyComplete();

    verify(subProjectPersistence).readById(1);
    verify(propertyGroupPersistence).readById(2);
    verify(subProjectPropertyGroupPersistence).create(any());
  }

  @Test
  void duplicate_shouldDuplicatePropertyGroups() {
    ProjectStage stage = new ProjectStage();
    stage.setId(1);
    PropertyGroup group = new PropertyGroup();
    group.setId(2);
    group.setName("GroupName");
    SubProjectPropertyGroup spg = new SubProjectPropertyGroup();
    spg.setStage(stage);
    spg.setPropertyGroup(group);

    PropertyGroup newGroup = new PropertyGroup();
    newGroup.setId(3);
    newGroup.setName("GroupName");

    when(propertyGroupPersistence.readById(2)).thenReturn(Mono.just(group));
    when(propertyGroupPersistence.create(any())).thenReturn(Mono.just(newGroup));
    when(subProjectPersistence.readById(anyInt())).thenReturn(Mono.just(stage));
    when(subProjectPropertyGroupPersistence.create(any())).thenAnswer(inv -> Mono.just(inv.getArgument(0)));

    StepVerifier.create(propertyGroupService.duplicate(List.of(spg)))
        .expectNextCount(1)
        .verifyComplete();

    verify(propertyGroupPersistence).readById(2);
    verify(propertyGroupPersistence).create(any());
    verify(subProjectPersistence).readById(1);
    verify(subProjectPropertyGroupPersistence).create(any());
  }

  @Test
  void update_shouldUpdatePropertyGroups() {
    PropertyGroup group = new PropertyGroup();
    group.setId(1);
    SubProjectPropertyGroup spg = new SubProjectPropertyGroup();
    spg.setPropertyGroup(group);

    when(propertyGroupPersistence.update(eq(VALUE_ONE), any())).thenReturn(Mono.just(group));

    StepVerifier.create(propertyGroupService.update(List.of(spg)))
        .expectNext(spg)
        .verifyComplete();

    verify(propertyGroupPersistence).update(eq(VALUE_ONE), any());
  }

  @Test
  void createDocument_shouldUploadFileAndPersistDocument() {
    Integer groupId = 1;
    PropertyGroupDocument doc = new PropertyGroupDocument();
    FilePart filePart = mock(FilePart.class);
    when(filePart.filename()).thenReturn("file.xlsx");
    when(fileUploadPersistence.uploadFile(filePart)).thenReturn(Mono.just("url"));
    when(propertyGroupPersistence.createDocument(eq(groupId), any())).thenReturn(Mono.just(doc));

    // No catalog detail, should not import properties
    StepVerifier.create(propertyGroupService.createDocument(groupId, doc, filePart))
        .expectNext(doc)
        .verifyComplete();

    verify(fileUploadPersistence).uploadFile(filePart);
    verify(propertyGroupPersistence).createDocument(eq(groupId), any());
  }

  @Test
  void deleteDocument_shouldDeleteDocument() {
    when(propertyGroupPersistence.deleteDocument(anyInt())).thenReturn(Mono.empty());

    StepVerifier.create(propertyGroupService.deleteDocument(5))
        .verifyComplete();

    verify(propertyGroupPersistence).deleteDocument(5);
  }
}