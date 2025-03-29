package es.miw.tfm.invierte.core.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import reactor.core.publisher.Mono;

public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

  private final JwtService jwtService;

  public JwtAuthenticationManager(JwtService jwtService) {
    this.jwtService = jwtService;
  }

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
