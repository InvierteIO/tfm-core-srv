package es.miw.tfm.invierte.core.infrastructure.api.resource;

import es.miw.tfm.invierte.core.domain.model.Membership;
import es.miw.tfm.invierte.core.domain.service.MembershipService;
import es.miw.tfm.invierte.core.infrastructure.api.Rest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
