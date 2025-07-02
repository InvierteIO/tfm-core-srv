package es.miw.tfm.invierte.core.infrastructure.data.persistence;

import es.miw.tfm.invierte.core.domain.persistence.FileUploadPersistence;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * Local file system implementation of the FileUploadPersistence interface.
 * Handles file uploads to a local directory for the 'dev' profile.
 * Generates a local URL for accessing the uploaded file.
 *
 * @author denilssonmn
 */
@Component
@Profile("dev")
@Slf4j
public class LocalFileUploadPersistenceImpl implements FileUploadPersistence {

  private final String folderPath;

  public LocalFileUploadPersistenceImpl(
      @Value("${app.upload-dir}") String folderPath) {
    this.folderPath = folderPath;
  }

  @Override
  public Mono<String> uploadFile(FilePart filePart) {
    Path uploadPath = Paths.get(folderPath);
    String key = UUID.randomUUID() + "_" + filePart.filename();
    Path targetLocation = uploadPath.resolve(key);

    return Mono.fromCallable(() -> {
      if (!Files.exists(uploadPath)) {
        Files.createDirectories(uploadPath);
      }
      return targetLocation.toFile();
    })
    .subscribeOn(Schedulers.boundedElastic())
    .flatMap(filePart::transferTo)
    .thenReturn(String.format("http://localhost:8081/files/%s", key));
  }


}
