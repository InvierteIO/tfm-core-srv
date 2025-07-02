package es.miw.tfm.invierte.core.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import es.miw.tfm.invierte.core.domain.model.Property;
import es.miw.tfm.invierte.core.domain.model.enums.CommercializationCycle;
import es.miw.tfm.invierte.core.domain.model.enums.Flag;
import es.miw.tfm.invierte.core.domain.persistence.PropertyPersistence;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class PropertyServiceTest {

  @Mock
  private PropertyPersistence propertyPersistence;

  @InjectMocks
  private PropertyService propertyService;

  private static final Integer VALUE_ONE = 1;

  @Test
  void create_shouldDelegateToPersistence() {
    Property property = Property.builder().name("Test").build();
    when(propertyPersistence.create(eq(property), eq(VALUE_ONE))).thenReturn(Mono.just(property));

    StepVerifier.create(propertyService.create(property, 1))
        .expectNext(property)
        .verifyComplete();

    verify(propertyPersistence).create(property, 1);
  }

  @Test
  void findBySubProjectPropertyGroupId_shouldDelegateToPersistence() {
    Property property = Property.builder().name("Test").build();
    when(propertyPersistence.findBySubProjectPropertyGroupId(2)).thenReturn(Flux.just(property));

    StepVerifier.create(propertyService.findBySubProjectPropertyGroupId(2))
        .expectNext(property)
        .verifyComplete();

    verify(propertyPersistence).findBySubProjectPropertyGroupId(2);
  }

  @Test
  void importFromExcel_shouldParsePropertiesCorrectly() throws Exception {
    Workbook workbook = new XSSFWorkbook();
    Sheet sheet = workbook.createSheet();
    Row header = sheet.createRow(0);
    header.createCell(0).setCellValue("Code");
    header.createCell(1).setCellValue("Name");
    header.createCell(2).setCellValue("AvailableSale");
    header.createCell(3).setCellValue("Price");
    header.createCell(4).setCellValue("Parking");
    header.createCell(5).setCellValue("Address");

    Row row = sheet.createRow(1);
    row.createCell(0).setCellValue("C1");
    row.createCell(1).setCellValue("Prop1");
    row.createCell(2).setCellValue(Flag.YES.name());
    row.createCell(3).setCellValue(12345.67);
    row.createCell(4).setCellValue(Flag.NO.name());
    row.createCell(5).setCellValue("Addr1");

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    workbook.write(out);
    workbook.close();

    ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());

    List<Property> properties = propertyService.importFromExcel(in);

    assertEquals(1, properties.size());
    Property p = properties.get(0);
    assertEquals("C1", p.getCodeEnterprise());
    assertEquals("Prop1", p.getName());
    assertTrue(p.isAvailableSale());
    assertEquals(12345.67, p.getPrice());
    assertFalse(p.isParkingSpace());
    assertEquals("Addr1", p.getAddress());
    assertEquals(CommercializationCycle.PRE_SALES, p.getCommercializationCycle());
    assertEquals("00000000", p.getCodeSystem());
  }

  @Test
  void delete_shouldDelegateToPersistence() {
    Property property = Property.builder().name("Test").build();
    when(propertyPersistence.delete(property)).thenReturn(Mono.empty());

    StepVerifier.create(propertyService.delete(property))
        .verifyComplete();

    verify(propertyPersistence).delete(property);
  }
}