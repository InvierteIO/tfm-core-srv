package es.miw.tfm.invierte.core.infrastructure.api.resource;

import es.miw.tfm.invierte.core.domain.model.RealStateCompany;
import es.miw.tfm.invierte.core.domain.service.RealStateCompanyService;
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

@Log4j2
@Rest
@RequestMapping(RealStateCompanyResource.REAL_STATE_COMPANIES)
@RequiredArgsConstructor
public class RealStateCompanyResource {

  public static final String REAL_STATE_COMPANIES = "/real-state-companies";

  public static final String REAL_STATE_TAX_IDENTIFICATION_NUMBER = "/{taxIdentificationNumber}";

  private final RealStateCompanyService realStateCompanyService;

  @PostMapping(produces = {"application/json"})
  @PreAuthorize("permitAll()")
  public Mono<RealStateCompany> create(@Valid @RequestBody RealStateCompany realStateCompany) {
    return this.realStateCompanyService.create(realStateCompany);
  }

  @GetMapping(REAL_STATE_TAX_IDENTIFICATION_NUMBER)
  @PreAuthorize("@securityUtil.hasRoleForCompanyCode('OWNER', #taxIdentificationNumber)")
  public Mono<RealStateCompany> read(@PathVariable String taxIdentificationNumber) {
    return this.realStateCompanyService.read(taxIdentificationNumber);
  }

  @PutMapping(REAL_STATE_TAX_IDENTIFICATION_NUMBER)
  @PreAuthorize("@securityUtil.hasRoleForCompanyCode('OWNER', #taxIdentificationNumber)")
  public Mono<RealStateCompany> update(@PathVariable String taxIdentificationNumber, @Valid @RequestBody RealStateCompany realStateCompany) {
    return this.realStateCompanyService.update(taxIdentificationNumber, realStateCompany);
  }

}
