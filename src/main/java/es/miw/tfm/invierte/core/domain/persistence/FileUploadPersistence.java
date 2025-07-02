package es.miw.tfm.invierte.core.domain.persistence;

import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

/**
 * Interface for file upload persistence strategies.
 * Defines a contract for uploading files and returning their accessible URL.
 * Implemented by local and AWS S3 persistence classes.
 *
 * @author denilssonmn
 */
public interface FileUploadPersistence {

  Mono<String> uploadFile(FilePart multipartFile);

}
