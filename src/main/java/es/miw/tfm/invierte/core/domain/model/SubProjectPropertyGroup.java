package es.miw.tfm.invierte.core.domain.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubProjectPropertyGroup {

  private Integer id;

  private ProjectStage stage;

  private PropertyGroup propertyGroup;

  private List<PropertyGroupDocument> propertyGroupDocuments = new ArrayList<>();

  private List<Property> properties = new ArrayList<>();

}
