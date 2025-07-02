package es.miw.tfm.invierte.core.infrastructure.data.persistence;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import es.miw.tfm.invierte.core.domain.persistence.FileUploadPersistence;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * AWS S3 implementation of the FileUploadPersistence interface.
 * Handles file uploads to an S3 bucket using the AWS SDK.
 * Configured for the 'prod' profile.
 *
 * @author denilssonmn
 */
@Component
@Profile("prod")
@Slf4j
public class AwsFileUploadPersistenceImpl implements FileUploadPersistence {

  private final AmazonS3 s3Client;

  private final String bucketName;

  /**
   * Constructs an AWS S3 file upload persistence implementation.
   * Initializes the S3 client with the provided bucket name and region.
   *
   * @param bucketName the name of the S3 bucket
   * @param region the AWS region where the bucket is located
   */
  public AwsFileUploadPersistenceImpl(
      @Value("${aws.s3.bucket-name}") String bucketName,
      @Value("${aws.s3.region}") String region) {
    this.bucketName = bucketName;
    this.s3Client =  AmazonS3ClientBuilder.standard()
        .withRegion(Regions.fromName(region))
        .build();
  }

  @Override
  public Mono<String> uploadFile(FilePart filePart) {
    return DataBufferUtils.join(filePart.content())
        .switchIfEmpty(Mono.error(new RuntimeException("Failed to read file content")))
        .flatMap(dataBuffer -> {
          byte[] bytes = new byte[dataBuffer.readableByteCount()];
          dataBuffer.read(bytes);
          DataBufferUtils.release(dataBuffer);

          ObjectMetadata metadata = new ObjectMetadata();
          metadata.setContentLength(bytes.length);

          String key = UUID.randomUUID() + "_" + filePart.filename();

          return Mono.fromCallable(() -> {
            try (ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes)) {
              s3Client.putObject(bucketName, key, inputStream, metadata);
              return s3Client.getUrl(bucketName, key).toString();
            } catch (IOException e) {
              log.error("Error uploading file to S3 {}. Error: {}",
                  filePart.filename(), e.getMessage());
              throw new IOException("Failed to upload file to S3: " + filePart.filename(), e);
            }
          }).subscribeOn(Schedulers.boundedElastic());
        });
  }

}
