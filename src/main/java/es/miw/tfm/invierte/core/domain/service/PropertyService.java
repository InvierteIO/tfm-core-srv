package es.miw.tfm.invierte.core.domain.service;


import es.miw.tfm.invierte.core.domain.model.Property;
import es.miw.tfm.invierte.core.domain.model.enums.CommercializationCycle;
import es.miw.tfm.invierte.core.domain.model.enums.Flag;
import es.miw.tfm.invierte.core.domain.persistence.PropertyPersistence;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service class for managing {@link Property} domain objects.
 * Provides business logic for creating, deleting, retrieving, and importing properties,
 * delegating persistence operations to the {@link PropertyPersistence} interface.
 * Supports importing property data from Excel files.
 *
 * @author denilssonmn
 */
@Service
@RequiredArgsConstructor
public class PropertyService {

  private final PropertyPersistence propertyPersistence;

  public Mono<Property> create(Property property, Integer subProjectPropertyGroupId) {
    return this.propertyPersistence.create(property, subProjectPropertyGroupId);
  }

  public Flux<Property> findBySubProjectPropertyGroupId(Integer subProjectPropertyGroupId) {
    return this.propertyPersistence.findBySubProjectPropertyGroupId(subProjectPropertyGroupId);
  }

  /**
   * Imports a list of {@link Property} objects from the provided Excel input stream.
   * Parses the first sheet, skipping the header row, and maps each row to a {@link Property}
   * using predefined columns for code, name, sale availability, price, parking, and address.
   *
   * @param inputStream the Excel file input stream to parse
   * @return a list of {@link Property} objects extracted from the Excel file
   * @throws RuntimeException if the Excel file cannot be parsed
   * @author denilssonmn
   */
  public List<Property> importFromExcel(InputStream inputStream) {

    List<Property> propiedades = new ArrayList<>();

    try (Workbook workbook = new XSSFWorkbook(inputStream)) {
      Sheet sheet = workbook.getSheetAt(0);

      for (int i = 1; i <= sheet.getLastRowNum(); i++) {
        Row row = sheet.getRow(i);
        if (row == null) {
          continue;
        }

        String codeEnterprise = row.getCell(0).getStringCellValue();
        String name = row.getCell(1).getStringCellValue();
        String availableSale = row.getCell(2).getStringCellValue();
        Double price = row.getCell(3).getNumericCellValue();
        String parkingSpace = row.getCell(4).getStringCellValue();
        String address = row.getCell(5).getStringCellValue();

        Property doc = Property.builder()
            .codeEnterprise(codeEnterprise)
            .codeSystem("00000000")
            .name(name)
            .isAvailableSale(!Flag.NO.name().equals(availableSale))
            .price(price)
            .commercializationCycle(CommercializationCycle.PRE_SALES)
            .isParkingSpace(!Flag.NO.name().equals(parkingSpace))
            .address(address)
            .build();

        propiedades.add(doc);
      }
      return propiedades;

    } catch (IOException e) {
      throw new RuntimeException("Failed to parse Excel", e);
    }
  }

  private Double safeParseDouble(String str) {
    if (str == null || str.isEmpty()) {
      return 0.0;
    }
    try {
      return Double.parseDouble(str);
    } catch (NumberFormatException e) {
      return 0.0;
    }
  }

  public Mono<Void> delete(Property property) {
    return this.propertyPersistence.delete(property);
  }
}
