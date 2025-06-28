package es.miw.tfm.invierte.core.domain.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.miw.tfm.invierte.core.infrastructure.data.entity.SubProjectEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocationCode {

  @NotBlank
  private Integer id;

  @NotBlank
  private String code;

  @NotBlank
  private String type;

  @NotBlank
  private String name;

}
