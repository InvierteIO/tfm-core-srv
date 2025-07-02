package es.miw.tfm.invierte.core.infrastructure.api.resource;

import es.miw.tfm.invierte.core.domain.exception.BadRequestException;
import es.miw.tfm.invierte.core.domain.model.PropertyGroupDocument;
import es.miw.tfm.invierte.core.domain.model.SubProjectPropertyGroup;
import es.miw.tfm.invierte.core.domain.service.PropertyGroupService;
import es.miw.tfm.invierte.core.infrastructure.api.util.FileUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RealEstatePropertyGroupResourceTest {

  @Mock
  private PropertyGroupService propertyGroupService;

  @InjectMocks
  private RealEstatePropertyGroupResource resource;

  @Test
  void shouldCreatePropertyGroups() {
    SubProjectPropertyGroup group = new SubProjectPropertyGroup();
    when(propertyGroupService.create(anyList())).thenReturn(Flux.just(group));

    Flux<SubProjectPropertyGroup> result = resource.create("TAX123", List.of(group));

    assertEquals(1, result.collectList().block().size());
    verify(propertyGroupService).create(anyList());
  }

  @Test
  void shouldGetSubProjectPropertyGroups() {
    SubProjectPropertyGroup group = new SubProjectPropertyGroup();
    when(propertyGroupService.readByTaxIdentificationNumberAndProjectId("TAX123", 1))
        .thenReturn(Flux.just(group));

    Flux<SubProjectPropertyGroup> result = resource.getSubProjectPropertyGroups("TAX123", 1);

    assertEquals(1, result.collectList().block().size());
    verify(propertyGroupService).readByTaxIdentificationNumberAndProjectId("TAX123", 1);
  }

  @Test
  void shouldDelete() {
    when(propertyGroupService.delete("TAX123", 1, 2)).thenReturn(Mono.empty());

    Mono<Void> result = resource.delete("TAX123", 1, 2);

    assertNull(result.block());
    verify(propertyGroupService).delete("TAX123", 1, 2);
  }

  @Test
  void shouldDeleteAllByPropertyGroup() {
    when(propertyGroupService.deleteAllByPropertyGroup("TAX123", 1, 3)).thenReturn(Mono.empty());

    Mono<Void> result = resource.deleteAllByPropertyGroup("TAX123", 1, 3);

    assertNull(result.block());
    verify(propertyGroupService).deleteAllByPropertyGroup("TAX123", 1, 3);
  }

  @Test
  void shouldAssignPropertyGroups() {
    SubProjectPropertyGroup group = new SubProjectPropertyGroup();
    when(propertyGroupService.assign(anyList())).thenReturn(Flux.just(group));

    Flux<SubProjectPropertyGroup> result = resource.assign("TAX123", List.of(group));

    assertEquals(1, result.collectList().block().size());
    verify(propertyGroupService).assign(anyList());
  }

  @Test
  void shouldDuplicatePropertyGroups() {
    SubProjectPropertyGroup group = new SubProjectPropertyGroup();
    when(propertyGroupService.duplicate(anyList())).thenReturn(Flux.just(group));

    Flux<SubProjectPropertyGroup> result = resource.duplicate("TAX123", List.of(group));

    assertEquals(1, result.collectList().block().size());
    verify(propertyGroupService).duplicate(anyList());
  }

  @Test
  void shouldUpdatePropertyGroups() {
    SubProjectPropertyGroup group = new SubProjectPropertyGroup();
    when(propertyGroupService.update(anyList())).thenReturn(Flux.just(group));

    Flux<SubProjectPropertyGroup> result = resource.update("TAX123", List.of(group));

    assertEquals(1, result.collectList().block().size());
    verify(propertyGroupService).update(anyList());
  }

  @Test
  void shouldCreateDocumentWhenFileAllowed() {
    FilePart filePart = mock(FilePart.class);
    String propertyGroupDocumentJson = "{}";
    PropertyGroupDocument propertyGroupDocument = new PropertyGroupDocument();

    try (MockedStatic<FileUtil> fileUtilMock = mockStatic(FileUtil.class)) {
      fileUtilMock.when(() -> FileUtil.isAllowedFile(filePart)).thenReturn(true);
      fileUtilMock.when(() -> FileUtil.parseJsonToPropertyGroupDocument(propertyGroupDocumentJson))
          .thenReturn(propertyGroupDocument);
      when(propertyGroupService.createDocument(eq(1), eq(propertyGroupDocument), eq(filePart)))
          .thenReturn(Mono.just(propertyGroupDocument));

      Mono<PropertyGroupDocument> result = resource.createDocument("TAX123", 1, filePart, propertyGroupDocumentJson);

      assertNotNull(result.block());
      verify(propertyGroupService).createDocument(eq(1), eq(propertyGroupDocument), eq(filePart));
    }
  }

  @Test
  void shouldReturnErrorWhenFileNotAllowed() {
    FilePart filePart = mock(FilePart.class);
    String propertyGroupDocumentJson = "{}";

    try (MockedStatic<FileUtil> fileUtilMock = mockStatic(FileUtil.class)) {
      fileUtilMock.when(() -> FileUtil.isAllowedFile(filePart)).thenReturn(false);

      Mono<PropertyGroupDocument> result = resource.createDocument("TAX123", 1, filePart, propertyGroupDocumentJson);

      assertThrows(BadRequestException.class, () -> result.block());
    }
  }

  @Test
  void shouldDeleteDocument() {
    when(propertyGroupService.deleteDocument(10)).thenReturn(Mono.empty());

    Mono<Void> result = resource.deleteDocument("TAX123", 1, 10);

    assertNull(result.block());
    verify(propertyGroupService).deleteDocument(10);
  }
}