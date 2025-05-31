package com.sanjeevnode.rms.foodservice.dto;

import com.sanjeevnode.rms.foodservice.enums.FoodFilterType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodFilterDTO {

    @NotNull(message = "Filter type cannot be null")
//    @Schema(allowableValues = {"NAME", "CATEGORY", "PRICE", "DESCRIPTION"})
    private FoodFilterType filterType;

    @NotNull(message = "Filter value cannot be null")
    private String value;
}
