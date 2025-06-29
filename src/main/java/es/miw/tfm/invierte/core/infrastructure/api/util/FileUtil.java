package es.miw.tfm.invierte.core.infrastructure.api.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.miw.tfm.invierte.core.domain.model.ProjectDocument;
import java.io.IOException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.multipart.FilePart;

/**
 * Utility class for file-related operations in the API layer.
 * Provides methods to validate uploaded file types and extensions.
 * Only JPG, PNG, and PDF files are considered allowed.
 *
 * <p>This class is not intended to be instantiated.
 *
 * @author denilssonmn
 */
@UtilityClass
@Slf4j
public class FileUtil {

  /**
   * Checks if the provided file is allowed based on its MIME type and extension.
   * Only files with JPG, JPEG, PNG, or PDF extensions and corresponding MIME types
   * are considered valid for upload.
   *
   * @param file the MultipartFile to validate
   * @return true if the file is allowed; false otherwise
   */
  public static boolean isAllowedFile(FilePart file) {
    String filename = file.filename().toLowerCase();

    return filename.endsWith(".jpg")
        || filename.endsWith(".jpeg")
        || filename.endsWith(".png")
        || filename.endsWith(".pdf");
  }

  /**
   * Parses the provided JSON string into a ProjectDocument object.
   * Uses Jackson's ObjectMapper for deserialization. Throws an
   * IllegalArgumentException if the JSON format is invalid.
   *
   * @param json the JSON string representing a ProjectDocument
   * @return the deserialized ProjectDocument object
   * @throws IllegalArgumentException if the JSON is invalid
   */
  public static ProjectDocument parseJsonToProjectDocument(String json) {
    try {
      log.info("json project document: {}", json);
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.readValue(json, ProjectDocument.class);
    } catch (IOException e) {
      throw new IllegalArgumentException("Invalid JSON format");
    }
  }

}
