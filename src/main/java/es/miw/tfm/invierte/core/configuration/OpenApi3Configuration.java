package es.miw.tfm.invierte.core.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up OpenAPI 3 documentation.
 * This class configures the OpenAPI definition and security scheme for the application.
 * It uses Swagger annotations to define the API documentation settings.
 *
 * <p>The security scheme is configured to use a bearer token with the JWT format.
 * This allows the API to support authentication using JWT tokens.
 *
 * @author denilssonmn
 * @author devcastlecix
 */
@Configuration
@OpenAPIDefinition
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer"
)
public class OpenApi3Configuration {

}
