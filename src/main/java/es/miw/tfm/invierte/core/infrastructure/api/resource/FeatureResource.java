package es.miw.tfm.invierte.core.infrastructure.api.resource;

import es.miw.tfm.invierte.core.domain.model.Feature;
import es.miw.tfm.invierte.core.domain.service.FeatureService;
import es.miw.tfm.invierte.core.infrastructure.api.Rest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;

/**
 * REST controller for handling feature-related endpoints.
 * Provides operations to retrieve available features in the system.
 * Delegates business logic to the {@link FeatureService}.
 *
 * @author denilssonmn
 */
@Log4j2
@Rest
@RequestMapping(FeatureResource.FEATURES)
@RequiredArgsConstructor
public class FeatureResource {

  public static final String FEATURES = "/features";

  private final FeatureService featureService;

  @GetMapping
  @PreAuthorize("authenticated")
  public Flux<Feature> readAll() {
    return this.featureService.readAll();
  }
}
