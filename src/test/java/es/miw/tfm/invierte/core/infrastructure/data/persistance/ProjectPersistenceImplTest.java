package es.miw.tfm.invierte.core.infrastructure.data.persistance;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import es.miw.tfm.invierte.core.domain.exception.NotFoundException;
import es.miw.tfm.invierte.core.domain.model.Project;
import es.miw.tfm.invierte.core.domain.model.ProjectDocument;
import es.miw.tfm.invierte.core.infrastructure.data.dao.CatalogDetailRepository;
import es.miw.tfm.invierte.core.infrastructure.data.dao.ProjectDocumentRepository;
import es.miw.tfm.invierte.core.infrastructure.data.dao.ProjectRepository;
import es.miw.tfm.invierte.core.infrastructure.data.entity.ProjectDocumentEntity;
import es.miw.tfm.invierte.core.infrastructure.data.entity.ProjectEntity;
import es.miw.tfm.invierte.core.infrastructure.data.persistence.ProjectPersistenceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class ProjectPersistenceImplTest {

  private ProjectRepository projectRepository;
  private ProjectDocumentRepository projectDocumentRepository;
  private CatalogDetailRepository catalogDetailRepository;
  private ProjectPersistenceImpl projectPersistence;

  @BeforeEach
  void setUp() {
    projectRepository = mock(ProjectRepository.class);
    projectDocumentRepository = mock(ProjectDocumentRepository.class);
    catalogDetailRepository = mock(CatalogDetailRepository.class);
    projectPersistence = new ProjectPersistenceImpl(
        projectRepository, projectDocumentRepository, catalogDetailRepository);
  }

  @Test
  void create_shouldSaveProject() {
    Project project = mock(Project.class);
    ProjectEntity entity = mock(ProjectEntity.class);
    Project expected = new Project();

    when(projectRepository.save(any(ProjectEntity.class))).thenReturn(entity);
    when(entity.toProject()).thenReturn(expected);

    Mono<Project> result = projectPersistence.create(project);

    StepVerifier.create(result)
        .expectNext(expected)
        .verifyComplete();

    verify(projectRepository).save(any(ProjectEntity.class));
    verify(entity).toProject();
  }

  @Test
  void update_shouldUpdateProject_whenExists() {
    Integer id = 1;
    Project project = mock(Project.class);
    ProjectEntity existing = mock(ProjectEntity.class);
    ProjectEntity updated = mock(ProjectEntity.class);
    Project expected = new Project();

    when(projectRepository.findById(id)).thenReturn(Optional.of(existing));
    when(existing.getId()).thenReturn(id);
    when(projectRepository.save(any(ProjectEntity.class))).thenReturn(updated);
    when(updated.toProject()).thenReturn(expected);

    Mono<Project> result = projectPersistence.update(id, project);

    StepVerifier.create(result)
        .expectNext(expected)
        .verifyComplete();

    verify(projectRepository).findById(id);
    verify(projectRepository).save(any(ProjectEntity.class));
    verify(updated).toProject();
  }

  @Test
  void update_shouldThrowNotFound_whenNotExists() {
    Integer id = 1;
    Project project = mock(Project.class);

    when(projectRepository.findById(id)).thenReturn(Optional.empty());

    Mono<Project> result = projectPersistence.update(id, project);

    StepVerifier.create(result)
        .expectError(NotFoundException.class)
        .verify();

    verify(projectRepository).findById(id);
  }

  @Test
  void readById_shouldReturnProject_whenExists() {
    Integer id = 1;
    ProjectEntity entity = mock(ProjectEntity.class);
    Project expected = new Project();

    when(projectRepository.findById(id)).thenReturn(Optional.of(entity));
    when(entity.toProject()).thenReturn(expected);

    Mono<Project> result = projectPersistence.readById(id);

    StepVerifier.create(result)
        .expectNext(expected)
        .verifyComplete();

    verify(projectRepository).findById(id);
    verify(entity).toProject();
  }


  @Test
  void readByTaxIdentificationNumberAndId_shouldReturnProject_whenExists() {
    String taxId = "TAX123";
    Integer projectId = 2;
    ProjectEntity entity = mock(ProjectEntity.class);
    Project expected = new Project();

    when(projectRepository.findByTaxIdentificationNumberAndId(taxId, projectId)).thenReturn(entity);
    when(entity.toProject()).thenReturn(expected);

    Mono<Project> result = projectPersistence.readByTaxIdentificationNumberAndId(taxId, projectId);

    StepVerifier.create(result)
        .expectNext(expected)
        .verifyComplete();

    verify(projectRepository).findByTaxIdentificationNumberAndId(taxId, projectId);
    verify(entity).toProject();
  }

  @Test
  void createDocument_shouldSaveDocument_whenProjectExists() {
    Integer projectId = 1;
    ProjectDocument projectDocument = mock(ProjectDocument.class);
    ProjectEntity projectEntity = mock(ProjectEntity.class);
    ProjectDocumentEntity documentEntity = mock(ProjectDocumentEntity.class);
    ProjectDocument expected = new ProjectDocument();

    when(projectRepository.findById(projectId)).thenReturn(Optional.of(projectEntity));
    when(projectDocumentRepository.save(any(ProjectDocumentEntity.class))).thenReturn(documentEntity);
    when(documentEntity.toProjectDocument()).thenReturn(expected);

    Mono<ProjectDocument> result = projectPersistence.createDocument(projectId, projectDocument);

    StepVerifier.create(result)
        .expectNext(expected)
        .verifyComplete();

    verify(projectRepository).findById(projectId);
    verify(projectDocumentRepository).save(any(ProjectDocumentEntity.class));
    verify(documentEntity).toProjectDocument();
  }

  @Test
  void createDocument_shouldThrowNotFound_whenProjectNotExists() {
    Integer projectId = 1;
    ProjectDocument projectDocument = mock(ProjectDocument.class);

    when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

    Mono<ProjectDocument> result = projectPersistence.createDocument(projectId, projectDocument);

    StepVerifier.create(result)
        .expectError(NotFoundException.class)
        .verify();

    verify(projectRepository).findById(projectId);
  }

  @Test
  void deleteDocument_shouldDelete() {
    Integer documentId = 1;

    doNothing().when(projectDocumentRepository).deleteById(documentId);

    Mono<Void> result = projectPersistence.deleteDocument(documentId);

    StepVerifier.create(result)
        .verifyComplete();

    verify(projectDocumentRepository).deleteById(documentId);
  }

  @Test
  void readAll_shouldReturnAllProjects() {
    ProjectEntity entity1 = mock(ProjectEntity.class);
    ProjectEntity entity2 = mock(ProjectEntity.class);
    Project project1 = new Project();
    Project project2 = new Project();

    when(projectRepository.findAll()).thenReturn(List.of(entity1, entity2));
    when(entity1.toProject()).thenReturn(project1);
    when(entity2.toProject()).thenReturn(project2);

    Flux<Project> result = projectPersistence.readAll();

    StepVerifier.create(result)
        .expectNext(project1)
        .expectNext(project2)
        .verifyComplete();

    verify(projectRepository).findAll();
    verify(entity1).toProject();
    verify(entity2).toProject();
  }
}