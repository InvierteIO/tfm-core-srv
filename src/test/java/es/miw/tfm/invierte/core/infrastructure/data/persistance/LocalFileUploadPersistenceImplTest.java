package es.miw.tfm.invierte.core.infrastructure.data.persistance;

import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import es.miw.tfm.invierte.core.infrastructure.data.persistence.LocalFileUploadPersistenceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class LocalFileUploadPersistenceImplTest {

  private LocalFileUploadPersistenceImpl persistence;
  private final String folderPath = "test-uploads";

  @BeforeEach
  void setUp() {
    persistence = new LocalFileUploadPersistenceImpl(folderPath);
  }

  @Test
  void uploadFile_shouldCreateDirectoryAndReturnUrl() {
    FilePart filePart = mock(FilePart.class);
    when(filePart.filename()).thenReturn("test.txt");
    when(filePart.transferTo(any(File.class))).thenReturn(Mono.empty());

    Path uploadPath = Paths.get(folderPath);

    try (MockedStatic<Files> filesMock = mockStatic(Files.class, CALLS_REAL_METHODS)) {
      filesMock.when(() -> Files.exists(uploadPath)).thenReturn(false);
      filesMock.when(() -> Files.createDirectories(uploadPath)).thenReturn(uploadPath);

      StepVerifier.create(persistence.uploadFile(filePart))
          .assertNext(url -> {
            Assertions.assertTrue(url.startsWith("http://localhost:8081/files/"));
            Assertions.assertTrue(url.endsWith("_test.txt"));
          })
          .verifyComplete();

      filesMock.verify(() -> Files.createDirectories(uploadPath));
    }
  }

  @Test
  void uploadFile_shouldNotCreateDirectoryIfExists() {
    FilePart filePart = mock(FilePart.class);
    when(filePart.filename()).thenReturn("exists.txt");
    when(filePart.transferTo(any(File.class))).thenReturn(Mono.empty());

    Path uploadPath = Paths.get(folderPath);

    try (MockedStatic<Files> filesMock = mockStatic(Files.class, CALLS_REAL_METHODS)) {
      filesMock.when(() -> Files.exists(uploadPath)).thenReturn(true);

      StepVerifier.create(persistence.uploadFile(filePart))
          .assertNext(url -> {
            Assertions.assertTrue(url.contains("_exists.txt"));
          })
          .verifyComplete();

      filesMock.verify(() -> Files.createDirectories(uploadPath), never());
    }
  }

  @Test
  void uploadFile_shouldPropagateTransferError() {
    FilePart filePart = mock(FilePart.class);
    when(filePart.filename()).thenReturn("fail.txt");
    when(filePart.transferTo(any(File.class))).thenReturn(Mono.error(new RuntimeException("transfer failed")));

    Path uploadPath = Paths.get(folderPath);

    try (MockedStatic<Files> filesMock = mockStatic(Files.class, CALLS_REAL_METHODS)) {
      filesMock.when(() -> Files.exists(uploadPath)).thenReturn(true);

      StepVerifier.create(persistence.uploadFile(filePart))
          .expectErrorMatches(e -> e instanceof RuntimeException && e.getMessage().equals("transfer failed"))
          .verify();
    }
  }
}