package es.miw.tfm.invierte.core.infrastructure.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasRole('OWNER') or hasRole('AGENT')")
@RestController
@SecurityRequirement(name = "bearerAuth")
public @interface Rest {

}
