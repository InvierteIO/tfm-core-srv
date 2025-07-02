package es.miw.tfm.invierte.core.infrastructure.data.persistance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.net.URL;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import es.miw.tfm.invierte.core.infrastructure.data.persistence.AwsFileUploadPersistenceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class AwsFileUploadPersistenceImplTest {

  private AmazonS3 s3Client;
  private AwsFileUploadPersistenceImpl persistence;

  @BeforeEach
  void setUp() {
    s3Client = mock(AmazonS3.class);
    // Use reflection to inject the mock S3 client
    persistence = new AwsFileUploadPersistenceImpl("test-bucket", "us-east-1");
    java.lang.reflect.Field field;
    try {
      field = AwsFileUploadPersistenceImpl.class.getDeclaredField("s3Client");
      field.setAccessible(true);
      field.set(persistence, s3Client);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  void uploadFile_shouldUploadAndReturnUrl() {
    // Arrange
    byte[] content = "test-content".getBytes();
    DataBuffer dataBuffer = new DefaultDataBufferFactory().wrap(content);

    FilePart filePart = mock(FilePart.class);
    when(filePart.content()).thenReturn(Flux.just(dataBuffer));
    when(filePart.filename()).thenReturn("file.txt");

    URL url = mock(URL.class);
    when(url.toString()).thenReturn("https://s3/test-bucket/file.txt");
    when(s3Client.getUrl(anyString(), anyString())).thenReturn(url);

    // Act & Assert
    StepVerifier.create(persistence.uploadFile(filePart))
        .assertNext(resultUrl -> assertEquals("https://s3/test-bucket/file.txt", resultUrl))
        .verifyComplete();

    // Verify S3 putObject was called
    ArgumentCaptor<String> bucketCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<ByteArrayInputStream> streamCaptor = ArgumentCaptor.forClass(ByteArrayInputStream.class);
    ArgumentCaptor<ObjectMetadata> metadataCaptor = ArgumentCaptor.forClass(ObjectMetadata.class);

    verify(s3Client).putObject(
        bucketCaptor.capture(),
        keyCaptor.capture(),
        streamCaptor.capture(),
        metadataCaptor.capture()
    );

    assertEquals("test-bucket", bucketCaptor.getValue());
    assertTrue(keyCaptor.getValue().endsWith("_file.txt"));
    assertEquals(content.length, metadataCaptor.getValue().getContentLength());
  }

  @Test
  void uploadFile_shouldErrorOnEmptyContent() {
    FilePart filePart = mock(FilePart.class);
    when(filePart.content()).thenReturn(Flux.empty());

    StepVerifier.create(persistence.uploadFile(filePart))
        .expectErrorMatches(e -> e instanceof RuntimeException &&
            e.getMessage().contains("Failed to read file content"))
        .verify();
  }
}