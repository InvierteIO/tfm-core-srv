package es.miw.tfm.invierte.core.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * WebFlux configuration for serving static resources in the 'dev' profile.
 * Maps requests to /files/** to the local upload directory specified by
 * the app.upload-dir property.
 *
 * @author denilssonmn
 */
@Configuration(proxyBeanMethods = false)
@EnableWebFlux
@Profile("dev")
public class WebFluxResourceConfig implements WebFluxConfigurer {

  private final String uploadDir;

  private WebFluxResourceConfig(@Value("${app.upload-dir}") String uploadDir) {
    this.uploadDir = uploadDir;
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/files/**")
        .addResourceLocations("file:" + uploadDir + "/");
  }

}
