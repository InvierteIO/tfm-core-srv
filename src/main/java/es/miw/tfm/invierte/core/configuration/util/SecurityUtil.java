package es.miw.tfm.invierte.core.configuration.util;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service("securityUtil")
@NoArgsConstructor
public class SecurityUtil {

  public static Mono<Boolean> hasRoleForCompanyCode(String role, String companyCode) {
    return ReactiveSecurityContextHolder.getContext()
        .map(SecurityContext::getAuthentication)
        .flatMap(authentication -> {
          if (authentication == null) {
            return Mono.just(false);
          }
          return Mono.just(isRoleForCompanyCode(role, companyCode, authentication.getAuthorities()));
        })
        .defaultIfEmpty(false);

  }

  private static Boolean isRoleForCompanyCode(String role, String companyCode, Collection<? extends GrantedAuthority> authorities) {
    return Stream.ofNullable(authorities)
        .flatMap(Collection::stream)
        .filter(Objects::nonNull)
        .map(GrantedAuthority::getAuthority)
        .anyMatch(companyRole -> companyRole.contains(companyCode + "_" + role));
  }

}
