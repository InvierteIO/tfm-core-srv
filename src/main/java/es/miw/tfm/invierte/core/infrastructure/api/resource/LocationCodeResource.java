package es.miw.tfm.invierte.core.infrastructure.api.resource;

import es.miw.tfm.invierte.core.domain.model.InfraInstallation;
import es.miw.tfm.invierte.core.domain.model.LocationCode;
import es.miw.tfm.invierte.core.domain.service.InfrastructureInstallationService;
import es.miw.tfm.invierte.core.domain.service.LocationCodeService;
import es.miw.tfm.invierte.core.infrastructure.api.Rest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;

/**
 * REST resource for managing location codes. Provides endpoints to
 * retrieve location code domain objects in a reactive manner.
 *
 * @author denilssonmn
 */
@Log4j2
@Rest
@RequestMapping(LocationCodeResource.LOCATION_CODES)
@RequiredArgsConstructor
public class LocationCodeResource {

  public static final String LOCATION_CODES = "/location-codes";

  private final LocationCodeService locationCodeService;

  @GetMapping
  @PreAuthorize("authenticated")
  public Flux<LocationCode> readAll() {
    return this.locationCodeService.readAll();
  }

}
