package es.miw.tfm.invierte.core.infrastructure.api.resource;

import es.miw.tfm.invierte.core.domain.model.Project;
import es.miw.tfm.invierte.core.domain.service.ProjectService;
import es.miw.tfm.invierte.core.infrastructure.api.Rest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping(RealStateCompanyProjectResource.COMPANY_PROJECT)
@RequiredArgsConstructor
public class RealStateCompanyProjectResource {

  public static final String REAL_STATE_COMPANIES = "/real-estate-companies";

  public static final String REAL_STATE_TAX_IDENTIFICATION_NUMBER = "/{taxIdentificationNumber}";

  public static final String COMPANY_PROJECT = REAL_STATE_COMPANIES + REAL_STATE_TAX_IDENTIFICATION_NUMBER + "/projects";

  public static final String PROJECT_ID = "/{projectId}";

  private final ProjectService projectService;

  @PostMapping(produces = {"application/json"})
  @PreAuthorize("@securityUtil.hasRoleForCompanyCode('OWNER', #taxIdentificationNumber)")
  public Mono<Project> create(@PathVariable String taxIdentificationNumber, @Valid @RequestBody Project project) {
    project.setTaxIdentificationNumber(taxIdentificationNumber);
    return this.projectService.create(project);
  }

  @PutMapping(PROJECT_ID)
  @PreAuthorize("@securityUtil.hasRoleForCompanyCode('OWNER', #taxIdentificationNumber)")
  public Mono<Project> update(@PathVariable String taxIdentificationNumber, @PathVariable Integer projectId,
      @Valid @RequestBody Project project) {
    project.setTaxIdentificationNumber(taxIdentificationNumber);
    return this.projectService.update(projectId, project);
  }

  @GetMapping(PROJECT_ID)
  @PreAuthorize("@securityUtil.hasRoleForCompanyCode('OWNER', #taxIdentificationNumber)")
  public Mono<Project> read(@PathVariable String taxIdentificationNumber, @PathVariable Integer projectId) {
    log.info("Read project {} for taxIdentificationNumber: {}", projectId, taxIdentificationNumber);
    return this.projectService.readById(projectId);
  }



}
