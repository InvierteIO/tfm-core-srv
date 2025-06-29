package es.miw.tfm.invierte.core.domain.service;

import es.miw.tfm.invierte.core.domain.model.Project;
import es.miw.tfm.invierte.core.domain.model.ProjectDocument;
import es.miw.tfm.invierte.core.domain.persistence.FileUploadPersistence;
import es.miw.tfm.invierte.core.domain.persistence.ProjectPersistence;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Service class for managing Project domain operations.
 * Delegates persistence operations to the {@link ProjectPersistence} interface.
 * Provides methods for creating, updating, and retrieving projects.
 *
 * @author denilssonmn
 */
@Service
@RequiredArgsConstructor
public class ProjectService {

  private final ProjectPersistence projectPersistence;

  private final FileUploadPersistence fileUploadPersistence;

  public Mono<Project> create(Project project) {
    project.setDefaultValues();
    return this.projectPersistence.create(project);
  }

  public Mono<Project> update(Integer id, Project project) {
    return this.projectPersistence.update(id, project);
  }

  public Mono<Project> readById(Integer id) {
    return this.projectPersistence.readById(id);
  }

  /**
   * Creates and persists a new project document for the specified project.
   * Uploads the provided file, sets its URL and filename in the document,
   * and delegates persistence to the ProjectPersistence interface.
   *
   * @param projectId the ID of the project to associate the document with
   * @param projectDocument the project document metadata to persist
   * @param file the file to upload and associate with the document
   * @return a Mono emitting the persisted ProjectDocument
   */
  public Mono<ProjectDocument> createDocument(Integer projectId,
      @Valid ProjectDocument projectDocument, FilePart file) {
    return this.fileUploadPersistence.uploadFile(file)
        .flatMap(url -> {
          projectDocument.setPath(url);
          projectDocument.setFilename(file.filename());
          return this.projectPersistence.createDocument(projectId, projectDocument);
        });
  }

  public Mono<Void> deleteDocument(Integer documentId) {
    return this.projectPersistence.deleteDocument(documentId);
  }
}
