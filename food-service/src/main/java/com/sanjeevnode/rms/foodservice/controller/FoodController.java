package com.sanjeevnode.rms.foodservice.controller;

import com.sanjeevnode.rms.foodservice.dto.CreateFoodDTO;
import com.sanjeevnode.rms.foodservice.dto.FoodFilterDTO;
import com.sanjeevnode.rms.foodservice.service.FoodService;
import com.sanjeevnode.rms.foodservice.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/food")
@Tag(name = "Restaurant Management System", description = "Food Service")
@AllArgsConstructor
public class FoodController {

    private FoodService foodService;


    @GetMapping("/health")
    @Operation(summary = "Health Check Endpoint")
    public String healthCheck() {
        return "Food Service is running";
    }

    @GetMapping("/")
    @Operation(summary = "Get Food Menu")
    public ResponseEntity<ApiResponse> getFoodMenu() {
        return foodService.getAllFoods().buildResponse();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Food by ID")
    public ResponseEntity<ApiResponse> getFoodById(@PathVariable  String id) {
        return foodService.getFoodById(id).buildResponse();
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new Food Item")
    public ResponseEntity<ApiResponse> createFood(@Valid  @RequestBody CreateFoodDTO createFoodDTO) {
        return foodService.createFood(createFoodDTO).buildResponse();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update Food Item")
    public ResponseEntity<ApiResponse> updateFood(@PathVariable String id,
                                                  @RequestParam(required = false) String description,
                                                  @RequestParam(required = false) Double price,
                                                  @RequestParam(required = false) String category) {
        return foodService.updateFood(id, description, price, category).buildResponse();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete Food Item")
    public ResponseEntity<ApiResponse> deleteFood(@PathVariable String id) {
        return foodService.deleteFood(id).buildResponse();
    }

    @GetMapping("/filter")
    @Operation(summary = "Filter Food Items")
    public ResponseEntity<ApiResponse> filter(@Valid @ParameterObject FoodFilterDTO filterDTO) {
        return foodService.filterFoods(filterDTO).buildResponse();
    }

}
