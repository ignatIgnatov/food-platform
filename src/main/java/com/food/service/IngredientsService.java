package com.food.service;

import com.food.dto.request.IngredientCategoryRequestDto;
import com.food.model.IngredientCategory;
import com.food.model.IngredientsItem;

import java.util.List;

public interface IngredientsService {

    IngredientCategory createIngredientCategory(IngredientCategoryRequestDto ingredientCategoryRequestDto);

    IngredientCategory findIngredientCategoryById(Long id);

    List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id);

    IngredientsItem createIngredientItem(Long restaurantId, String ingredientName, Long ingredientCategoryId);

    List<IngredientsItem> findRestaurantIngredients(Long restaurantId);

    IngredientsItem updateStock(Long id);

}
