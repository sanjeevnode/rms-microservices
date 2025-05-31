package com.sanjeevnode.rms.foodservice.dto;

import com.sanjeevnode.rms.foodservice.model.Food;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodResponseDTO {
    private String id;
    private String name;
    private String description;
    private double price;
    private String category;
    private String createdAt;
    private String updatedAt;

    public FoodResponseDTO(Food food){
        this.id = food.getId();
        this.name = food.getName();
        this.description = food.getDescription();
        this.price = food.getPrice();
        this.category = food.getCategory().name();
        this.createdAt = food.getCreatedAt();
        this.updatedAt = food.getUpdatedAt();
    }
}
