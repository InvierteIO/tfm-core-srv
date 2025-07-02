package es.miw.tfm.invierte.core.infrastructure.api.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.miw.tfm.invierte.core.domain.model.ProjectDocument;
import es.miw.tfm.invierte.core.domain.model.PropertyGroupDocument;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.codec.multipart.FilePart;

class FileUtilTest {

  @Test
  void isAllowedFile_shouldReturnTrueForAllowedExtensions() {
    String[] allowed = {
        "test.jpg", "test.jpeg", "test.png", "test.pdf",
        "test.kml", "test.kmz", "test.xls", "test.xlsx"
    };
    for (String filename : allowed) {
      FilePart filePart = Mockito.mock(FilePart.class);
      Mockito.when(filePart.filename()).thenReturn(filename);
      assertTrue(FileUtil.isAllowedFile(filePart), "Should allow: " + filename);
    }
  }

  @Test
  void isAllowedFile_shouldReturnFalseForDisallowedExtensions() {
    String[] disallowed = {"test.gif", "test.txt", "test.doc", "test.exe"};
    for (String filename : disallowed) {
      FilePart filePart = Mockito.mock(FilePart.class);
      Mockito.when(filePart.filename()).thenReturn(filename);
      assertFalse(FileUtil.isAllowedFile(filePart), "Should not allow: " + filename);
    }
  }

  @Test
  void parseJsonToProjectDocument_shouldParseValidJson() throws JsonProcessingException {
    ProjectDocument doc = new ProjectDocument();
    doc.setName("Test");
    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(doc);

    ProjectDocument result = FileUtil.parseJsonToProjectDocument(json);
    assertNotNull(result);
    assertEquals("Test", result.getName());
  }

  @Test
  void parseJsonToProjectDocument_shouldThrowOnInvalidJson() {
    String invalidJson = "{invalid json}";
    assertThrows(IllegalArgumentException.class, () ->
        FileUtil.parseJsonToProjectDocument(invalidJson)
    );
  }

  @Test
  void parseJsonToPropertyGroupDocument_shouldParseValidJson() throws JsonProcessingException {
    PropertyGroupDocument doc = new PropertyGroupDocument();
    doc.setName("GroupDoc");
    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(doc);

    PropertyGroupDocument result = FileUtil.parseJsonToPropertyGroupDocument(json);
    assertNotNull(result);
    assertEquals("GroupDoc", result.getName());
  }

  @Test
  void parseJsonToPropertyGroupDocument_shouldThrowOnInvalidJson() {
    String invalidJson = "{invalid json}";
    assertThrows(IllegalArgumentException.class, () ->
        FileUtil.parseJsonToPropertyGroupDocument(invalidJson)
    );
  }
}