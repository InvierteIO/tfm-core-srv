package es.miw.tfm.invierte.core.infrastructure.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

/**
 * Custom annotation for REST controllers in the application.
 * This annotation combines the functionality of `@RestController`,
 * `@PreAuthorize`, and `@SecurityRequirement` to simplify the configuration
 * of REST endpoints with security and role-based access control.
 *
 * <p>Classes annotated with `@Rest` are automatically secured to allow access
 * only to users with the roles `OWNER` or `AGENT`.
 *
 * @see RestController
 * @see PreAuthorize
 * @see SecurityRequirement
 *
 * @author denilssonmn
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasRole('OWNER') or hasRole('AGENT')")
@RestController
@SecurityRequirement(name = "bearerAuth")
public @interface Rest {

}
