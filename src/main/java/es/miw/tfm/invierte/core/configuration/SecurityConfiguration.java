package es.miw.tfm.invierte.core.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import reactor.core.publisher.Mono;

/**
 * Configuration class for setting up security in the application.
 * This class configures security filters and authentication mechanisms using Spring Security.
 * It enables reactive method security and WebFlux security.
 *
 * @author denilssonmn
 * @author devcastlecix
 */
@Configuration
@EnableReactiveMethodSecurity
@EnableWebFluxSecurity
public class SecurityConfiguration {

  private final JwtService jwtService;

  /**
   * Constructor for SecurityConfiguration.
   * Initializes the JwtService used for token extraction and validation.
   *
   * @param jwtService the service for handling JWT operations
   */
  @Autowired
  public SecurityConfiguration(JwtService jwtService) {
    this.jwtService = jwtService;
  }

  /**
   * Configures the security filter chain for the application.
   * Disables CSRF, sets a no-op security context repository,
   * and adds a custom authentication filter.
   *
   * @param http the ServerHttpSecurity object for configuring security
   * @return the configured SecurityWebFilterChain
   */
  @Bean
  public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {

    ServerWebExchangeMatcher csrfProtectedEndpoints =
        ServerWebExchangeMatchers.pathMatchers("/secure/**", "/admin/**");

    return http
        .csrf(csrfSpec -> csrfSpec.requireCsrfProtectionMatcher(csrfProtectedEndpoints))
        .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
        .addFilterAt(bearerAuthenticationFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
        .build();
  }

  /**
   * Creates a custom authentication filter for handling bearer token authentication.
   * Extracts the token from the Authorization header and creates an authentication token.
   *
   * @return the configured AuthenticationWebFilter
   */
  private AuthenticationWebFilter bearerAuthenticationFilter() {
    AuthenticationWebFilter bearerAuthenticationFilter =
        new AuthenticationWebFilter(new JwtAuthenticationManager(jwtService));
    bearerAuthenticationFilter.setServerAuthenticationConverter(serverWebExchange -> {
      String token = jwtService.extractBearerToken(
          serverWebExchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
      return Mono.just(new UsernamePasswordAuthenticationToken(token, token));
    });
    return bearerAuthenticationFilter;
  }


}
