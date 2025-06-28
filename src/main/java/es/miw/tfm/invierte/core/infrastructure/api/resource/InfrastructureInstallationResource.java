package es.miw.tfm.invierte.core.infrastructure.api.resource;

import es.miw.tfm.invierte.core.domain.model.Bank;
import es.miw.tfm.invierte.core.domain.model.InfraInstallation;
import es.miw.tfm.invierte.core.domain.service.BankService;
import es.miw.tfm.invierte.core.domain.service.InfrastructureInstallationService;
import es.miw.tfm.invierte.core.infrastructure.api.Rest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;

/**
 * REST resource for managing infrastructure installations. Provides endpoints
 * to retrieve all infrastructure installations for authenticated users.
 *
 * @author denilssonmn
 */
@Log4j2
@Rest
@RequestMapping(InfrastructureInstallationResource.INFRA_INSTALLATION)
@RequiredArgsConstructor
public class InfrastructureInstallationResource {

  public static final String INFRA_INSTALLATION = "/infra-installations";

  private final InfrastructureInstallationService infrastructureInstallationService;

  @GetMapping
  @PreAuthorize("authenticated")
  public Flux<InfraInstallation> readAll() {
    return this.infrastructureInstallationService.readAll();
  }

}
