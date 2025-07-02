package es.miw.tfm.invierte.core.domain.service;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import es.miw.tfm.invierte.core.domain.model.Project;
import es.miw.tfm.invierte.core.domain.model.ProjectDocument;
import es.miw.tfm.invierte.core.domain.model.Property;
import es.miw.tfm.invierte.core.domain.model.enums.ProjectStatus;
import es.miw.tfm.invierte.core.domain.model.enums.PropertyCategory;
import es.miw.tfm.invierte.core.domain.persistence.FileUploadPersistence;
import es.miw.tfm.invierte.core.domain.persistence.ProjectPersistence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

  @Mock
  private ProjectPersistence projectPersistence;
  @Mock
  private PropertyGroupService propertyGroupService;
  @Mock
  private FileUploadPersistence fileUploadPersistence;

  @InjectMocks
  private ProjectService projectService;

  @Test
  void create_shouldPersistProject() {
    Project project = mock(Project.class);
    when(projectPersistence.create(project)).thenReturn(Mono.just(project));

    StepVerifier.create(projectService.create(project))
        .expectNext(project)
        .verifyComplete();

    verify(projectPersistence).create(project);
    verify(project).setDefaultValues();
  }

  @Test
  void update_shouldUpdateProject() {
    Project project = new Project();
    when(projectPersistence.update(1, project)).thenReturn(Mono.just(project));

    StepVerifier.create(projectService.update(1, project))
        .expectNext(project)
        .verifyComplete();

    verify(projectPersistence).update(1, project);
  }

  @Test
  void readById_shouldReturnProject() {
    Project project = new Project();
    when(projectPersistence.readById(1)).thenReturn(Mono.just(project));

    StepVerifier.create(projectService.readById(1))
        .expectNext(project)
        .verifyComplete();

    verify(projectPersistence).readById(1);
  }

  @Test
  void createDocument_shouldUploadFileAndPersistDocument() {
    Integer projectId = 1;
    ProjectDocument doc = new ProjectDocument();
    FilePart filePart = mock(FilePart.class);
    when(filePart.filename()).thenReturn("test.pdf");
    when(fileUploadPersistence.uploadFile(filePart)).thenReturn(Mono.just("url"));
    when(projectPersistence.createDocument(eq(projectId), any(ProjectDocument.class)))
        .thenReturn(Mono.just(doc));

    StepVerifier.create(projectService.createDocument(projectId, doc, filePart))
        .expectNext(doc)
        .verifyComplete();

    verify(fileUploadPersistence).uploadFile(filePart);
    verify(projectPersistence).createDocument(eq(projectId), argThat(projectDocument ->
        projectDocument.getPath().equals("url") && projectDocument.getFilename().equals("test.pdf")));
  }

  @Test
  void deleteDocument_shouldDelegateToPersistence() {
    int documentId = 99;
    when(projectPersistence.deleteDocument(documentId)).thenReturn(Mono.empty());

    StepVerifier.create(projectService.deleteDocument(documentId))
        .verifyComplete();

    verify(projectPersistence).deleteDocument(documentId);
  }

  @Test
  void findProjectSummariesByPropertyTypeAndStatus_shouldReturnFilteredSummaries() {
    // Arrange
    Project project = mock(Project.class);
    when(project.getId()).thenReturn(1);
    when(project.getName()).thenReturn("Test Project");
    when(project.getOfficeAddress()).thenReturn("Test Address");
    when(project.getStages()).thenReturn(2);
    when(project.getStatus()).thenReturn(ProjectStatus.ACTIVE);
    when(project.getTaxIdentificationNumber()).thenReturn("TAX123");

    // PropertyGroup and SubProjectPropertyGroup mocks
    var propertyGroup = mock(es.miw.tfm.invierte.core.domain.model.PropertyGroup.class);
    when(propertyGroup.getPropertyCategory()).thenReturn(PropertyCategory.APARTMENT);
    when(propertyGroup.getArea()).thenReturn(100.0);

    var subProjectPropertyGroup = mock(es.miw.tfm.invierte.core.domain.model.SubProjectPropertyGroup.class);
    when(subProjectPropertyGroup.getPropertyGroup()).thenReturn(propertyGroup);
    when(subProjectPropertyGroup.getProperties())
        .thenReturn(java.util.List.of(new Property(), new Property()));

    // Mock persistence and service
    when(projectPersistence.readAll()).thenReturn(Flux.just(project));
    when(propertyGroupService.readByTaxIdentificationNumberAndProjectId("TAX123", 1))
        .thenReturn(Flux.just(subProjectPropertyGroup));

    // Act & Assert
    StepVerifier.create(projectService.findProjectSummariesByPropertyTypeAndStatus(
            "APARTMENT", "ACTIVE", "TAX123"))
        .assertNext(dto -> {
          Assertions.assertEquals(1, dto.getId());
          Assertions.assertEquals("Test Project", dto.getName());
          Assertions.assertEquals("Test Address", dto.getAddress());
          Assertions.assertEquals(2, dto.getStages());
          Assertions.assertEquals(ProjectStatus.ACTIVE, dto.getStatus());
          // Assuming acumularTotales sums apartments count and area
          Assertions.assertEquals(2, dto.getNumberApartments());
          Assertions.assertEquals(0, dto.getNumberLands());
          Assertions.assertEquals(0, dto.getNumberHouses());
          Assertions.assertEquals(100.0, dto.getAreaTotal());
        })
        .verifyComplete();

    verify(projectPersistence).readAll();
    verify(propertyGroupService).readByTaxIdentificationNumberAndProjectId("TAX123", 1);
  }

}