package es.miw.tfm.invierte.core.infrastructure.api.resource;


import es.miw.tfm.invierte.core.domain.exception.BadRequestException;
import es.miw.tfm.invierte.core.domain.model.ProjectDocument;
import es.miw.tfm.invierte.core.domain.model.PropertyGroupDocument;
import es.miw.tfm.invierte.core.domain.model.SubProjectPropertyGroup;
import es.miw.tfm.invierte.core.domain.service.PropertyGroupService;
import es.miw.tfm.invierte.core.infrastructure.api.Rest;
import es.miw.tfm.invierte.core.infrastructure.api.util.FileUtil;
import jakarta.validation.Valid;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestPart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * REST resource for managing property groups of real estate companies.
 * Handles creation of SubProjectPropertyGroup entities for a given company.
 *
 * @author denilssonmn
 */
@Log4j2
@Rest
@RequestMapping(RealEstatePropertyGroupResource.REAL_STATE_AND_TAX_IDENTIFICATION_NUMBER)
@RequiredArgsConstructor
public class RealEstatePropertyGroupResource {

  public static final String REAL_STATE_COMPANIES = "/real-estate-companies";

  public static final String TAX_IDENTIFICATION_NUMBER = "/{taxIdentificationNumber}";

  public static final String REAL_STATE_AND_TAX_IDENTIFICATION_NUMBER = REAL_STATE_COMPANIES
      + TAX_IDENTIFICATION_NUMBER ;

  public static final String PROPERTY_GROUP = "/property-groups";

  public static final String STAGE_PROPERTY_GROUP = "/stage-property-groups";

  public static final String PROJECTS = "/projects";

  public static final String ASSIGN = "/assign";

  public static final String DUPLICATE = "/duplicate";

  public static final String DOCUMENTS = "/documents";

  public static final String DOCUMENT_ID = "/{documentId}";

  public static final String PROJECT_ID = "/{projectId}";

  public static final String PROPERTY_GROUP_ID = "/{propertyGroupId}";

  public static final String STAGE_PROPERTY_GROUP_ID = "/{stagePropertyGroupId}";

  private final PropertyGroupService propertyGroupService;

  /**
   * Creates property group associations for a given real estate company.
   * Delegates to {@link PropertyGroupService#create(List)} to persist the associations.
   *
   * @param taxIdentificationNumber the tax identification number of the company
   * @param subProjectPropertyGroupList the list of associations to create
   * @return a {@link Flux} emitting the created associations
   */
  @PostMapping(PROPERTY_GROUP)
  @PreAuthorize("@securityUtil.hasRoleForCompanyCode('OWNER', #taxIdentificationNumber)")
  public Flux<SubProjectPropertyGroup> create(@PathVariable String taxIdentificationNumber,
      @Valid @RequestBody List<SubProjectPropertyGroup> subProjectPropertyGroupList) {
    log.info("create property group: {} for taxIdentificationNumber {}",
        subProjectPropertyGroupList, taxIdentificationNumber);
    return this.propertyGroupService.create(subProjectPropertyGroupList);
  }

  /**
   * Retrieves property group associations for a specific project and real estate company.
   * Delegates to
   * {@link PropertyGroupService#readByTaxIdentificationNumberAndProjectId(String, Integer)}
   * to fetch the associations.
   *
   * @param taxIdentificationNumber the tax identification number of the company
   * @param projectId the identifier of the project
   * @return a {@link Flux} emitting the found {@link SubProjectPropertyGroup} associations
   */
  @GetMapping(PROJECTS + PROJECT_ID + PROPERTY_GROUP)
  @PreAuthorize("@securityUtil.hasRoleForCompanyCode('OWNER', #taxIdentificationNumber)")
  public Flux<SubProjectPropertyGroup> getSubProjectPropertyGroups(
      @PathVariable String taxIdentificationNumber,
      @PathVariable Integer projectId) {
    log.info("Get properties group for taxIdentificationNumber {} and projectId {}",
        taxIdentificationNumber, projectId);
    return this.propertyGroupService
        .readByTaxIdentificationNumberAndProjectId(taxIdentificationNumber, projectId);
  }

  /**
   * Deletes a property group association for a specific project stage and real
   * estate company. Delegates to {@link PropertyGroupService#delete(String,
   * Integer, Integer)} to perform the deletion.
   *
   * @param taxIdentificationNumber the tax identification number of the company
   * @param projectId the identifier of the project
   * @param stagePropertyGroupId the identifier of the stage property group
   * @return a {@link Mono} signaling completion when the association is deleted
   * @author denilssonmn
   */
  @DeleteMapping(PROJECTS + PROJECT_ID +  STAGE_PROPERTY_GROUP + STAGE_PROPERTY_GROUP_ID)
  @PreAuthorize("@securityUtil.hasRoleForCompanyCode('OWNER', #taxIdentificationNumber)")
  public Mono<Void> delete(@PathVariable String taxIdentificationNumber,
      @PathVariable Integer projectId, @PathVariable Integer stagePropertyGroupId) {
    log.info("Delete property group for taxIdentificationNumber {}"
            + " and projectId {} and stagePropertyGroupId {}",
        taxIdentificationNumber, projectId, stagePropertyGroupId);
    return this.propertyGroupService.delete(taxIdentificationNumber,
        projectId, stagePropertyGroupId);
  }

  /**
   * Deletes all property group associations for a specific property group,
   * project, and real estate company. Delegates to
   * {@link PropertyGroupService#deleteAllByPropertyGroup(String, Integer,
   * Integer)} to perform the deletion.
   *
   * @param taxIdentificationNumber the tax identification number of the company
   * @param projectId the identifier of the project
   * @param propertyGroupId the identifier of the property group
   * @return a {@link Mono} signaling completion when all associations are deleted
   * @author denilssonmn
   */
  @DeleteMapping(PROJECTS + PROJECT_ID + PROPERTY_GROUP + PROPERTY_GROUP_ID)
  @PreAuthorize("@securityUtil.hasRoleForCompanyCode('OWNER', #taxIdentificationNumber)")
  public Mono<Void> deleteAllByPropertyGroup(@PathVariable String taxIdentificationNumber,
      @PathVariable Integer projectId, @PathVariable Integer propertyGroupId) {
    log.info("Delete all property group for taxIdentificationNumber {}"
            + " and projectId {} and propertyGroupId {}",
        taxIdentificationNumber, projectId, propertyGroupId);
    return this.propertyGroupService
        .deleteAllByPropertyGroup(taxIdentificationNumber, projectId, propertyGroupId);
  }

  /**
   * Assigns property group associations to subprojects for a given real estate
   * company. Delegates to {@link PropertyGroupService#assign(List)} to persist
   * the associations.
   *
   * @param taxIdentificationNumber the tax identification number of the company
   * @param subProjectPropertyGroupList the list of associations to assign
   * @return a {@link Flux} emitting the assigned associations
   * @author denilssonmn
   */
  @PostMapping(PROPERTY_GROUP + ASSIGN)
  @PreAuthorize("@securityUtil.hasRoleForCompanyCode('OWNER', #taxIdentificationNumber)")
  public Flux<SubProjectPropertyGroup> assign(@PathVariable String taxIdentificationNumber,
      @Valid @RequestBody List<SubProjectPropertyGroup> subProjectPropertyGroupList) {
    log.info("Assign property group: {} for taxIdentificationNumber {}",
        subProjectPropertyGroupList, taxIdentificationNumber);
    return this.propertyGroupService.assign(subProjectPropertyGroupList);
  }

  /**
   * Duplicates property group associations for a given real estate company.
   * Delegates to {@link PropertyGroupService#duplicate(List)} to create new
   * property group associations based on the provided list.
   *
   * @param taxIdentificationNumber the tax identification number of the company
   * @param subProjectPropertyGroupList the list of associations to duplicate
   * @return a {@link Flux} emitting the duplicated associations
   * @author denilssonmn
   */
  @PostMapping(PROPERTY_GROUP + DUPLICATE)
  @PreAuthorize("@securityUtil.hasRoleForCompanyCode('OWNER', #taxIdentificationNumber)")
  public Flux<SubProjectPropertyGroup> duplicate(@PathVariable String taxIdentificationNumber,
      @Valid @RequestBody List<SubProjectPropertyGroup> subProjectPropertyGroupList) {
    log.info("duplicate property group: {} for taxIdentificationNumber {}",
        subProjectPropertyGroupList, taxIdentificationNumber);
    return this.propertyGroupService.duplicate(subProjectPropertyGroupList);
  }

  /**
   * Updates property group associations for a given real estate company.
   * Delegates to {@link PropertyGroupService#update(List)} to persist the
   * updated associations.
   *
   * @param taxIdentificationNumber the tax identification number of the company
   * @param subProjectPropertyGroupList the list of associations to update
   * @return a {@link Flux} emitting the updated associations
   * @author denilssonmn
   */
  @PutMapping(PROPERTY_GROUP)
  @PreAuthorize("@securityUtil.hasRoleForCompanyCode('OWNER', #taxIdentificationNumber)")
  public Flux<SubProjectPropertyGroup> update(@PathVariable String taxIdentificationNumber,
      @Valid @RequestBody List<SubProjectPropertyGroup> subProjectPropertyGroupList) {
    log.info("update property-group: {} for taxIdentificationNumber {}",
        subProjectPropertyGroupList, taxIdentificationNumber);
    return this.propertyGroupService.update(subProjectPropertyGroupList);
  }

  /**
   * Creates and persists a new document for the specified property group.
   * Validates the uploaded file type, parses the document metadata from JSON,
   * and delegates persistence to the PropertyGroupService. Only JPG, PNG, and PDF
   * files are allowed.
   *
   * @param taxIdentificationNumber the tax identification number of the company
   * @param propertyGroupId the ID of the property group to associate the document with
   * @param file the file to upload and associate with the document
   * @param propertyGroupDocument the JSON string representing the document metadata
   * @return a {@link Mono} emitting the persisted {@link PropertyGroupDocument}, or a bad request
   *         response if the file type is not allowed
   * @author denilssonmn
   */
  @PostMapping(PROPERTY_GROUP + PROPERTY_GROUP_ID + DOCUMENTS)
  @PreAuthorize("@securityUtil.hasRoleForCompanyCode('OWNER', #taxIdentificationNumber)")
  public Mono<PropertyGroupDocument> createDocument(@PathVariable String taxIdentificationNumber,
      @PathVariable Integer propertyGroupId,
      @RequestPart("file") FilePart file,
      @RequestPart("propertyGroupDocument") String propertyGroupDocument
  ) {
    log.info("Create document for property group {} and taxIdentificationNumber: {}",
        propertyGroupId, taxIdentificationNumber);

    if (!FileUtil.isAllowedFile(file)) {
      return Mono.error(new BadRequestException("File has not allowed format"));
    }

    final var propertyGroupDocumentDto = FileUtil.parseJsonToPropertyGroupDocument(
        propertyGroupDocument);
    return this.propertyGroupService. createDocument(propertyGroupId,
        propertyGroupDocumentDto, file);
  }

  /**
   * Deletes a document associated with the specified property group.
   * Only users with the OWNER role for the company can perform this operation.
   * Delegates the deletion to the {@link PropertyGroupService#deleteDocument(Integer)}.
   *
   * @param taxIdentificationNumber the tax identification number of the company
   * @param propertyGroupId the ID of the property group containing the document
   * @param documentId the ID of the document to delete
   * @return a {@link Mono} signaling completion when the document is deleted
   * @author denilssonmn
   */
  @DeleteMapping(PROPERTY_GROUP + PROPERTY_GROUP_ID + DOCUMENTS + DOCUMENT_ID)
  @PreAuthorize("@securityUtil.hasRoleForCompanyCode('OWNER', #taxIdentificationNumber)")
  public Mono<Void> deleteDocument(@PathVariable String taxIdentificationNumber,
      @PathVariable Integer propertyGroupId,
      @PathVariable Integer documentId
  ) {
    log.info("Delete property-group document {} for property group {}"
            + " and taxIdentificationNumber: {}",
        documentId, propertyGroupId, taxIdentificationNumber);
    return this.propertyGroupService.deleteDocument(documentId);
  }

}
