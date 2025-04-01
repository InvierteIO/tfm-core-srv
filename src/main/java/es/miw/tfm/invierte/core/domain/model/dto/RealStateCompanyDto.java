package es.miw.tfm.invierte.core.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class RealStateCompanyDto {
    @NotBlank
    private String name;

    @NotBlank
    private String taxIdentificationNumber;

    private String address;

    private String phone;
}
