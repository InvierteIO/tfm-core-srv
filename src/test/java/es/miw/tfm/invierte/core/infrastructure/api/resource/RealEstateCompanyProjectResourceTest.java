package es.miw.tfm.invierte.core.infrastructure.api.resource;

import es.miw.tfm.invierte.core.domain.exception.BadRequestException;
import es.miw.tfm.invierte.core.domain.model.Project;
import es.miw.tfm.invierte.core.domain.model.ProjectDocument;
import es.miw.tfm.invierte.core.domain.model.dto.ProjectSummaryDto;
import es.miw.tfm.invierte.core.domain.service.ProjectService;
import es.miw.tfm.invierte.core.infrastructure.api.util.FileUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RealEstateCompanyProjectResourceTest {

  @Mock
  private ProjectService projectService;

  @InjectMocks
  private RealEstateCompanyProjectResource resource;

  @Test
  void shouldCreateProject() {
    Project project = new Project();
    project.setTaxIdentificationNumber("TAX123");
    when(projectService.create(any(Project.class))).thenReturn(Mono.just(project));

    Mono<Project> result = resource.create("TAX123", project);

    assertEquals("TAX123", result.block().getTaxIdentificationNumber());
    verify(projectService).create(any(Project.class));
  }

  @Test
  void shouldUpdateProject() {
    Project project = new Project();
    project.setTaxIdentificationNumber("TAX123");
    when(projectService.update(eq(1), any(Project.class))).thenReturn(Mono.just(project));

    Mono<Project> result = resource.update("TAX123", 1, project);

    assertEquals("TAX123", result.block().getTaxIdentificationNumber());
    verify(projectService).update(eq(1), any(Project.class));
  }

  @Test
  void shouldReadProject() {
    Project project = new Project();
    when(projectService.readById(1)).thenReturn(Mono.just(project));

    Mono<Project> result = resource.read("TAX123", 1);

    assertNotNull(result.block());
    verify(projectService).readById(1);
  }

  @Test
  void shouldReadProjectSummary() {
    ProjectSummaryDto dto = new ProjectSummaryDto();
    when(projectService.findProjectSummariesByPropertyTypeAndStatus("APARTMENT", "ACTIVE", "TAX123"))
        .thenReturn(Flux.just(dto));

    Flux<ProjectSummaryDto> result = resource.read("TAX123", "APARTMENT", "ACTIVE");

    assertEquals(1, result.collectList().block().size());
    verify(projectService).findProjectSummariesByPropertyTypeAndStatus("APARTMENT", "ACTIVE", "TAX123");
  }

  @Test
  void shouldCreateDocumentWhenFileAllowed() {
    FilePart filePart = mock(FilePart.class);
    String projectDocumentJson = "{}";
    ProjectDocument projectDocument = new ProjectDocument();

    try (MockedStatic<FileUtil> fileUtilMock = mockStatic(FileUtil.class)) {
      fileUtilMock.when(() -> FileUtil.isAllowedFile(filePart)).thenReturn(true);
      fileUtilMock.when(() -> FileUtil.parseJsonToProjectDocument(projectDocumentJson)).thenReturn(projectDocument);
      when(projectService.createDocument(eq(1), eq(projectDocument), eq(filePart)))
          .thenReturn(Mono.just(projectDocument));

      Mono<ProjectDocument> result = resource.createDocument("TAX123", 1, filePart, projectDocumentJson);

      assertNotNull(result.block());
      verify(projectService).createDocument(eq(1), eq(projectDocument), eq(filePart));
    }
  }

  @Test
  void shouldReturnErrorWhenFileNotAllowed() {
    FilePart filePart = mock(FilePart.class);
    String projectDocumentJson = "{}";

    try (MockedStatic<FileUtil> fileUtilMock = mockStatic(FileUtil.class)) {
      fileUtilMock.when(() -> FileUtil.isAllowedFile(filePart)).thenReturn(false);

      Mono<ProjectDocument> result = resource.createDocument("TAX123", 1, filePart, projectDocumentJson);

      assertThrows(BadRequestException.class, () -> result.block());
    }
  }

  @Test
  void shouldDeleteDocument() {
    when(projectService.deleteDocument(10)).thenReturn(Mono.empty());

    Mono<Void> result = resource.deleteDocument("TAX123", 1, 10);

    assertNull(result.block());
    verify(projectService).deleteDocument(10);
  }
}