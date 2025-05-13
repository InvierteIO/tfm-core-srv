package es.miw.tfm.invierte.core.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * Configuration class for setting up WebFlux in the application.
 * This class configures CORS mappings to allow cross-origin requests from specific origins.
 * It implements the WebFluxConfigurer interface to customize WebFlux settings.
 *
 * @author denilssonmn
 * @author devcastlecix
 */
@Configuration
@EnableWebFlux
public class WebFluxConfiguration implements WebFluxConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOriginPatterns(
            "https://*.invierte.io",
            "http://localhost:*"
        )
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
        .maxAge(3600);
  }

}
