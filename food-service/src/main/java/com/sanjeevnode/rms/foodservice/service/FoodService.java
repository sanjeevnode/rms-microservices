package com.sanjeevnode.rms.foodservice.service;

import com.sanjeevnode.rms.foodservice.dto.CreateFoodDTO;
import com.sanjeevnode.rms.foodservice.dto.FoodFilterDTO;
import com.sanjeevnode.rms.foodservice.dto.FoodResponseDTO;
import com.sanjeevnode.rms.foodservice.enums.FoodCategory;
import com.sanjeevnode.rms.foodservice.exception.FoodAlreadyExistException;
import com.sanjeevnode.rms.foodservice.exception.FoodNotFoundException;
import com.sanjeevnode.rms.foodservice.model.Food;
import com.sanjeevnode.rms.foodservice.repository.FoodRepository;
import com.sanjeevnode.rms.foodservice.utils.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FoodService {

    private FoodRepository foodRepository;

    public ApiResponse getAllFoods() {

        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("Fetched all foods successfully")
                .data(foodRepository.findAll())
                .build();

    }

    public ApiResponse getFoodById(String id) {
        return foodRepository.findById(id)
                .map(food -> ApiResponse.builder()
                        .status(HttpStatus.OK)
                        .message("Fetched food successfully")
                        .data(food)
                        .build())
                .orElse(ApiResponse.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message("Food not found")
                        .build());
    }

    public ApiResponse createFood(CreateFoodDTO food) {
      if(foodRepository.findByName(food.getName()).isPresent()) {
          throw new FoodAlreadyExistException(food.getName());
      }
      Food newFood = foodRepository.save(food.toFood());
      return ApiResponse.builder()
              .status(HttpStatus.CREATED)
              .message("Food created successfully")
              .data(new FoodResponseDTO(newFood))
              .build();
    }

    public ApiResponse updateFood(String id,String description,double price, String category){
        var food = foodRepository.findById(id)
                .orElseThrow(() -> new FoodNotFoundException(id));
        if(description!=null && !description.isEmpty()) {
            food.setDescription(description);
        }
        if(price > 0) {
            food.setPrice(price);
        }
        if(category != null && !category.isEmpty()) {
            food.setCategory(FoodCategory.valueOf(category.toUpperCase()));
        }
        Food updatedFood = foodRepository.save(food);

        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("Food updated successfully")
                .data(new FoodResponseDTO(updatedFood))
                .build();
    }

    public ApiResponse deleteFood(String id) {
        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new FoodNotFoundException(id));
        foodRepository.delete(food);
        return ApiResponse.builder()
                .status(HttpStatus.NO_CONTENT)
                .message("Food deleted successfully")
                .build();
    }

    public ApiResponse filterFoods(FoodFilterDTO filterDTO) {
        List<Food> allFoods = foodRepository.findAll();
        List<Food> filteredFoods;

        switch (filterDTO.getFilterType()) {
            case NAME -> filteredFoods = allFoods.stream()
                    .filter(food -> food.getName().toLowerCase().contains(filterDTO.getValue().toLowerCase()))
                    .collect(Collectors.toList());

            case DESCRIPTION -> filteredFoods = allFoods.stream()
                    .filter(food -> food.getDescription().toLowerCase().contains(filterDTO.getValue().toLowerCase()))
                    .collect(Collectors.toList());

            case CATEGORY -> {
                try {
                    FoodCategory category = FoodCategory.valueOf(filterDTO.getValue().toUpperCase());
                    filteredFoods = allFoods.stream()
                            .filter(food -> food.getCategory() == category)
                            .collect(Collectors.toList());
                } catch (IllegalArgumentException e) {
                    return ApiResponse.builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .message("Invalid category: " + filterDTO.getValue())
                            .build();
                }
            }

            case PRICE -> {
                filteredFoods = allFoods.stream()
                        .filter(food -> matchPriceCondition(food.getPrice(), filterDTO.getValue()))
                        .collect(Collectors.toList());
            }

            default -> {
                return ApiResponse.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .message("Invalid filter type")
                        .build();
            }
        }

        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("Filtered foods successfully")
                .data(filteredFoods)
                .build();
    }

    private boolean matchPriceCondition(double price, String condition) {
        condition = condition.replaceAll("\\s+", ""); // remove any spaces
        try {
            if (condition.contains("&")) { // e.g., ">=100&<200"
                String[] parts = condition.split("&");
                for (String part : parts) {
                    if (!matchPriceCondition(price, part)) return false;
                }
                return true;
            } else if (condition.startsWith(">=")) {
                double val = Double.parseDouble(condition.substring(2));
                return price >= val;
            } else if (condition.startsWith("<=")) {
                double val = Double.parseDouble(condition.substring(2));
                return price <= val;
            } else if (condition.startsWith(">")) {
                double val = Double.parseDouble(condition.substring(1));
                return price > val;
            } else if (condition.startsWith("<")) {
                double val = Double.parseDouble(condition.substring(1));
                return price < val;
            } else if (condition.startsWith("=")) {
                double val = Double.parseDouble(condition.substring(1));
                return price == val;
            } else {
                // default to exact match
                double val = Double.parseDouble(condition);
                return price == val;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }


}
