package es.miw.tfm.invierte.core.domain.service;

import es.miw.tfm.invierte.core.domain.model.Project;
import es.miw.tfm.invierte.core.domain.model.ProjectDocument;
import es.miw.tfm.invierte.core.domain.model.dto.ProjectSummaryDto;
import es.miw.tfm.invierte.core.domain.model.enums.ProjectStatus;
import es.miw.tfm.invierte.core.domain.model.enums.PropertyCategory;
import es.miw.tfm.invierte.core.domain.persistence.FileUploadPersistence;
import es.miw.tfm.invierte.core.domain.persistence.ProjectPersistence;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
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

  private final PropertyGroupService propertyGroupService;

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

  /**
   * Retrieves a list of {@link ProjectSummaryDto} objects filtered by property type,
   * project status, and company tax identification number.
   * Aggregates project and property group data, including counts of apartments, lands,
   * houses, and total area, for each matching project.
   *
   * @param propertyType the type of property to filter by (e.g., APARTMENT, LAND, HOUSE)
   * @param projectStatus the status of the project to filter by
   * @param taxIdentificationNumber the tax identification number of the company
   * @return a {@link Flux} emitting the filtered {@link ProjectSummaryDto} objects
   * @author denilssonmn
   */
  public Flux<ProjectSummaryDto> findProjectSummariesByPropertyTypeAndStatus(
      String propertyType, String projectStatus, String taxIdentificationNumber) {

    final var projectStatusEnum = ProjectStatus.valueOf(projectStatus);
    final var propertyCategoryEnum = PropertyCategory.valueOf(propertyType);

    return projectPersistence.readAll()
        .filter(project -> taxIdentificationNumber.equals(project.getTaxIdentificationNumber()))
        .filter(project -> projectStatusEnum.equals(project.getStatus()))
        .flatMap(project ->
            propertyGroupService.readByTaxIdentificationNumberAndProjectId(
                    project.getTaxIdentificationNumber(), project.getId()
                )
                .collectList()
                .filter(propertyGroups ->
                    propertyGroups.stream()
                        .anyMatch(pg -> propertyCategoryEnum.equals(
                            pg.getPropertyGroup().getPropertyCategory()))
                )
                .map(propertyGroups -> {
                  ProjectSummaryDto dto = new ProjectSummaryDto();
                  dto.setId(project.getId());
                  dto.setName(project.getName());
                  dto.setAddress(project.getOfficeAddress());
                  dto.setStages(project.getStages());
                  dto.setStatus(project.getStatus());

                  for (var group : propertyGroups) {
                    final var category = group.getPropertyGroup().getPropertyCategory();
                    int countApartments = PropertyCategory.APARTMENT.equals(category)
                        ? group.getProperties().size() : 0;
                    int countLands = PropertyCategory.LAND.equals(category)
                        ? group.getProperties().size() : 0;
                    int countHouses = PropertyCategory.HOUSE.equals(category)
                        ? group.getProperties().size() : 0;
                    double areaTotal = group.getPropertyGroup().getArea() != null
                        ? group.getPropertyGroup().getArea() : 0.0;

                    dto.acumularTotales(countApartments, countLands, countHouses, areaTotal);
                  }
                  return dto;
                })
        );
  }


}
