package com.food.service;

import com.food.dto.request.IngredientCategoryRequestDto;
import com.food.dto.response.IngredientCategoryResponseDto;
import com.food.dto.response.IngredientItemResponseDto;
import com.food.model.IngredientCategory;
import com.food.model.IngredientsItem;

import java.util.List;

public interface IngredientsService {

    IngredientCategoryResponseDto createIngredientCategory(IngredientCategoryRequestDto ingredientCategoryRequestDto);

    IngredientCategory findIngredientCategoryById(Long id);

    List<IngredientCategoryResponseDto> findIngredientCategoryByRestaurantId(Long id);

    IngredientItemResponseDto createIngredientItem(Long restaurantId, String ingredientName, Long ingredientCategoryId);

    List<IngredientItemResponseDto> findRestaurantIngredients(Long restaurantId);

    IngredientsItem updateStock(Long id);

}
