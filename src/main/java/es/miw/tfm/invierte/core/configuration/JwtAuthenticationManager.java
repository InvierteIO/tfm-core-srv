package es.miw.tfm.invierte.core.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import reactor.core.publisher.Mono;

/**
 * Custom authentication manager for handling JWT-based authentication.
 * This class implements the ReactiveAuthenticationManager interface to authenticate users
 * based on JWT tokens. It extracts user details and roles from the token and creates
 * an authentication object.
 *
 * @author denilssonmn
 * @author devcastlecix
 */
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

  private final JwtService jwtService;

  /**
   * Constructor for JwtAuthenticationManager.
   * Initializes the JwtService used for extracting and verifying JWT tokens.
   *
   * @param jwtService the service for handling JWT operations
   */
  public JwtAuthenticationManager(JwtService jwtService) {
    this.jwtService = jwtService;
  }

  /**
   * Authenticates a user based on the provided JWT token.
   * Extracts the user's roles and company-specific roles from the token and creates
   * a UsernamePasswordAuthenticationToken with the extracted details.
   *
   * @param authentication the authentication object containing the JWT token
   * @return a Mono emitting the authenticated Authentication object
   */
  @Override
  public Mono<Authentication> authenticate(Authentication authentication) {
    List<GrantedAuthority> authorities = new ArrayList<>();

    String token = authentication.getCredentials().toString();
    if (!token.isBlank()) {
      authorities.add(new SimpleGrantedAuthority("ROLE_" + jwtService.role(token)));
    }

    jwtService.roles(token)
        .forEach((companyKey, roleValue) ->
            authorities.add(new SimpleGrantedAuthority("ROLE_" + companyKey + "_" + roleValue)));

    return Mono.just(new UsernamePasswordAuthenticationToken(
        jwtService.user(token), authentication.getCredentials(), authorities));
  }
}
