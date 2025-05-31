package com.sanjeevnode.rms.foodservice.repository;

import com.sanjeevnode.rms.foodservice.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food,String> {

    Optional<Food> findByName(String name);
}
