package com.food.service;

import com.food.dto.request.CreateFoodRequest;
import com.food.dto.response.FoodResponseDto;
import com.food.model.Category;
import com.food.model.Food;
import com.food.model.Restaurant;

import java.util.List;

public interface FoodService {
    FoodResponseDto createFood(CreateFoodRequest createFoodRequest, Category category, Restaurant restaurant);

    void deleteFood(Long foodId);

    List<FoodResponseDto> getRestaurantsFood(Long restaurantId, boolean isVegetarian, boolean isNonVeg, boolean isSeasonal, String foodCategory);

    List<Food> searchFood(String keyword);

    Food findFoodById(Long foodId);

    Food updateAvailabilityStatus(Long foodId);

}
