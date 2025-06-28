package es.miw.tfm.invierte.core.infrastructure.api.resource;

import es.miw.tfm.invierte.core.domain.model.FinancialBonus;
import es.miw.tfm.invierte.core.domain.service.FinancialBonusService;
import es.miw.tfm.invierte.core.infrastructure.api.Rest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;

/**
 * REST controller for handling financial bonus-related endpoints.
 * Provides operations to retrieve available financial bonuses in the system.
 * Delegates business logic to the {@link FinancialBonusService}.
 *
 * @author denilssonmn
 */
@Log4j2
@Rest
@RequestMapping(FinancialBonusResource.FINANCIAL_BONUSES)
@RequiredArgsConstructor
public class FinancialBonusResource {

  public static final String FINANCIAL_BONUSES = "/financial-bonuses";

  private final FinancialBonusService financialBonusService;

  @GetMapping
  @PreAuthorize("authenticated")
  public Flux<FinancialBonus> readAll() {
    return this.financialBonusService.readAll();
  }

}
