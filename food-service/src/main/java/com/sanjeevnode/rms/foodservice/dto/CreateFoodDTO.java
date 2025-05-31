package com.sanjeevnode.rms.foodservice.dto;

import com.sanjeevnode.rms.foodservice.enums.FoodCategory;
import com.sanjeevnode.rms.foodservice.model.Food;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateFoodDTO {

    @NotNull(message = "Name cannot be null")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    private String name;

    @NotNull(message = "Description cannot be null")
    private String description;

    @NotNull(message = "Price cannot be null")
    private double price;

    @NotNull(message = "Category cannot be null")
    @Schema(allowableValues = {"VEG","NON_VEG"})
    private String category;

    public Food toFood() {
        Food food = new Food();
        food.setName(this.name);
        food.setDescription(this.description);
        food.setPrice(this.price);
        food.setCategory(FoodCategory.valueOf(this.category.toUpperCase()));
        return food;
    }
}
