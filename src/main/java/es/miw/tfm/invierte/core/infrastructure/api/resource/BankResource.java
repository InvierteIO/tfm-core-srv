package es.miw.tfm.invierte.core.infrastructure.api.resource;

import es.miw.tfm.invierte.core.domain.model.Bank;
import es.miw.tfm.invierte.core.domain.service.BankService;
import es.miw.tfm.invierte.core.infrastructure.api.Rest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;

/**
 * REST controller for handling bank-related endpoints.
 * Provides operations to retrieve available banks in the system.
 * Delegates business logic to the {@link BankService}.
 *
 * @author denilssonmn
 */
@Log4j2
@Rest
@RequestMapping(BankResource.BANKS)
@RequiredArgsConstructor
public class BankResource {

  public static final String BANKS = "/banks";

  private final BankService bankService;

  @GetMapping
  @PreAuthorize("authenticated")
  public Flux<Bank> readAll() {
    return this.bankService.readAll();
  }

}
