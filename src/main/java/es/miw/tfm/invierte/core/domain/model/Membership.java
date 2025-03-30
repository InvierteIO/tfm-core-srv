package es.miw.tfm.invierte.core.domain.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.miw.tfm.invierte.core.domain.model.validations.PositiveBigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Membership {
    private Integer id;
    @NotBlank
    private String levelName;
    @NotBlank
    private String shortDescription;
    private String longDescription;
    @PositiveBigDecimal
    private BigDecimal monthlyCost;
    @PositiveBigDecimal
    private BigDecimal annualCost;
    @PositiveOrZero
    private int maxRealtors;
    @PositiveOrZero
    private int maxProjects;
}
