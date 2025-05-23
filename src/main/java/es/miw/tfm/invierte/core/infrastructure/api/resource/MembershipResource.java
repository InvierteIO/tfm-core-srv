package es.miw.tfm.invierte.core.infrastructure.api.resource;

import es.miw.tfm.invierte.core.domain.model.Membership;
import es.miw.tfm.invierte.core.domain.service.MembershipService;
import es.miw.tfm.invierte.core.infrastructure.api.Rest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * REST controller for managing Memberships.
 * This class provides endpoints for creating, reading, updating, and deleting memberships.
 * It uses the `MembershipService` to perform the necessary operations.
 *
 * <p>All endpoints are secured and require appropriate permissions to access.
 *
 * @see MembershipService
 *
 * @author denilssonmn
 */
@Log4j2
@Rest
@RequestMapping(MembershipResource.MEMBERSHIPS)
@RequiredArgsConstructor
public class MembershipResource {
  public static final String MEMBERSHIPS = "/memberships";
  public static final String MEMBERSHIP_ID = "/{id}";
  private final MembershipService membershipService;

  @PostMapping(produces = { "application/json" })
  @PreAuthorize("permitAll()")
  public Mono<Membership> create(@Valid @RequestBody final Membership membership) {
    return this.membershipService.create(membership);
  }

  @DeleteMapping(MEMBERSHIP_ID)
  @PreAuthorize("permitAll()")
  public Mono<Void> delete(@PathVariable final Integer id) {
    return this.membershipService.delete(id);
  }

  @PutMapping(MEMBERSHIP_ID)
  @PreAuthorize("permitAll()")
  public Mono<Membership> update(@PathVariable final Integer id,
                                 @Valid @RequestBody final Membership membership) {
    return this.membershipService.update(id, membership);
  }

  @GetMapping(MEMBERSHIP_ID)
  @PreAuthorize("permitAll()")
  public Mono<Membership> read(@PathVariable final Integer id) {
    return this.membershipService.read(id);
  }

  @GetMapping
  @PreAuthorize("permitAll()")
  public Flux<Membership> readAll() {
    return this.membershipService.read();
  }
}
