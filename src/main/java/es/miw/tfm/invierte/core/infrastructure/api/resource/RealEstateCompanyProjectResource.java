package es.miw.tfm.invierte.core.infrastructure.api.resource;

import es.miw.tfm.invierte.core.domain.exception.BadRequestException;
import es.miw.tfm.invierte.core.domain.model.Project;
import es.miw.tfm.invierte.core.domain.model.ProjectDocument;
import es.miw.tfm.invierte.core.domain.model.dto.ProjectSummaryDto;
import es.miw.tfm.invierte.core.domain.service.ProjectService;
import es.miw.tfm.invierte.core.infrastructure.api.Rest;
import es.miw.tfm.invierte.core.infrastructure.api.util.FileUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * REST controller for managing Projects.
 * This class provides endpoints for creating and updating projects.
 * It uses the {@link ProjectService} to perform the necessary operations.
 *
 * <p>All endpoints are secured and require the user to have the OWNER
 * role for the specified company.
 *
 * @see ProjectService
 *
 * @author denilssonmn
 */

@Log4j2
@Rest
@RequestMapping(RealEstateCompanyProjectResource.COMPANY_PROJECT)
@RequiredArgsConstructor
public class RealEstateCompanyProjectResource {

  public static final String REAL_STATE_COMPANIES = "/real-estate-companies";

  public static final String REAL_STATE_TAX_IDENTIFICATION_NUMBER = "/{taxIdentificationNumber}";

  public static final String COMPANY_PROJECT = REAL_STATE_COMPANIES
      + REAL_STATE_TAX_IDENTIFICATION_NUMBER + "/projects";

  public static final String PROJECT_ID = "/{projectId}";

  public static final String DOCUMENT_ID = "/{documentId}";

  private static final String DOCUMENTS = "/documents";

  private static final String SUMMARY = "/summary";

  private final ProjectService projectService;

  @PostMapping(produces = {"application/json"})
  @PreAuthorize("@securityUtil.hasRoleForCompanyCode('OWNER', #taxIdentificationNumber)")
  public Mono<Project> create(@PathVariable String taxIdentificationNumber,
      @Valid @RequestBody Project project) {
    project.setTaxIdentificationNumber(taxIdentificationNumber);
    return this.projectService.create(project);
  }

  @PutMapping(PROJECT_ID)
  @PreAuthorize("@securityUtil.hasRoleForCompanyCode('OWNER', #taxIdentificationNumber)")
  public Mono<Project> update(@PathVariable String taxIdentificationNumber,
      @PathVariable Integer projectId, @Valid @RequestBody Project project) {
    project.setTaxIdentificationNumber(taxIdentificationNumber);
    return this.projectService.update(projectId, project);
  }

  @GetMapping(PROJECT_ID)
  @PreAuthorize("@securityUtil.hasRoleForCompanyCode('OWNER', #taxIdentificationNumber)")
  public Mono<Project> read(@PathVariable String taxIdentificationNumber,
      @PathVariable Integer projectId) {
    log.info("Read project {} for taxIdentificationNumber: {}", projectId, taxIdentificationNumber);
    return this.projectService.readById(projectId);
  }

  /**
   * Retrieves a list of {@link ProjectSummaryDto} objects for the specified company,
   * filtered by property type and project status.
   * Only users with the OWNER role for the company can access this endpoint.
   *
   * @param taxIdentificationNumber the tax identification number of the company
   * @param propertyType the type of property to filter by (e.g., APARTMENT, LAND, HOUSE)
   * @param projectStatus the status of the project to filter by
   * @return a {@link Flux} emitting the filtered {@link ProjectSummaryDto} objects
   * @author denilssonmn
   */
  @GetMapping(SUMMARY)
  @PreAuthorize("@securityUtil.hasRoleForCompanyCode('OWNER', #taxIdentificationNumber)")
  public Flux<ProjectSummaryDto> read(@PathVariable String taxIdentificationNumber,
      @RequestParam String propertyType, @RequestParam String projectStatus) {
    log.info("Read project summary for taxIdentificationNumber: {}", taxIdentificationNumber);
    return this.projectService.findProjectSummariesByPropertyTypeAndStatus(propertyType,
        projectStatus, taxIdentificationNumber);
  }

  /**
   * Creates and persists a new document for the specified project.
   * Validates the uploaded file type, sets document metadata, and delegates
   * persistence to the ProjectService. Only JPG, PNG, and PDF files are allowed.
   *
   * @param taxIdentificationNumber the tax identification number of the company
   * @param projectId the ID of the project to associate the document with
   * @param file the file to upload and associate with the document
   * @param projectDocument the project document metadata to persist
   * @return a Mono emitting the persisted ProjectDocument, or a bad request
   *         response if the file type is not allowed
   */
  @PostMapping(PROJECT_ID + DOCUMENTS)
  @PreAuthorize("@securityUtil.hasRoleForCompanyCode('OWNER', #taxIdentificationNumber)")
  public Mono<ProjectDocument> createDocument(@PathVariable String taxIdentificationNumber,
      @PathVariable Integer projectId,
      @RequestPart("file") FilePart file,
      @RequestPart("projectDocument") String projectDocument
  ) {
    log.info("Create document for project {} and taxIdentificationNumber: {}",
        projectId, taxIdentificationNumber);

    if (!FileUtil.isAllowedFile(file)) {
      return Mono.error(new BadRequestException("File has not allowed format"));
    }

    final var projectDocumentDto = FileUtil.parseJsonToProjectDocument(projectDocument);
    return this.projectService.createDocument(projectId, projectDocumentDto, file);
  }

  /**
   * Deletes a document associated with the specified project.
   * Delegates the deletion to the ProjectService. Only users with the OWNER
   * role for the company can perform this operation.
   *
   * @param taxIdentificationNumber the tax identification number of the company
   * @param projectId the ID of the project containing the document
   * @param documentId the ID of the document to delete
   * @return a Mono signaling completion when the document is deleted
   */
  @DeleteMapping(PROJECT_ID + DOCUMENTS + DOCUMENT_ID)
  @PreAuthorize("@securityUtil.hasRoleForCompanyCode('OWNER', #taxIdentificationNumber)")
  public Mono<Void> deleteDocument(@PathVariable String taxIdentificationNumber,
      @PathVariable Integer projectId,
      @PathVariable Integer documentId
  ) {
    log.info("Delete document {} for project {} and taxIdentificationNumber: {}",
        documentId, projectId, taxIdentificationNumber);
    return this.projectService.deleteDocument(documentId);
  }

}
