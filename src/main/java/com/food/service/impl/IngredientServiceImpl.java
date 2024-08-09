package com.food.service.impl;

import com.food.dto.request.IngredientCategoryRequestDto;
import com.food.exception.ingredient.IngredientCategoryNotFoundException;
import com.food.exception.ingredient.IngredientItemNotFoundException;
import com.food.model.IngredientCategory;
import com.food.model.IngredientsItem;
import com.food.model.Restaurant;
import com.food.repository.IngredientCategoryRepository;
import com.food.repository.IngredientItemRepository;
import com.food.service.IngredientsService;
import com.food.service.RestaurantService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientsService {

  private final IngredientItemRepository ingredientItemRepository;
  private final IngredientCategoryRepository ingredientCategoryRepository;
  private final RestaurantService restaurantService;

  @Override
  public IngredientCategory createIngredientCategory(IngredientCategoryRequestDto request) {
    Restaurant restaurant = restaurantService.findRestaurantById(request.getRestaurantId());

    IngredientCategory ingredientCategory = new IngredientCategory();
    ingredientCategory.setRestaurant(restaurant);
    ingredientCategory.setName(request.getName());

    return ingredientCategoryRepository.save(ingredientCategory);
  }

  @Override
  public IngredientCategory findIngredientCategoryById(Long id) {
    return ingredientCategoryRepository
        .findById(id)
        .orElseThrow(IngredientCategoryNotFoundException::new);
  }

  @Override
  public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) {
    restaurantService.findRestaurantById(id);
    return ingredientCategoryRepository.findByRestaurantId(id);
  }

  @Override
  public IngredientsItem createIngredientItem(
      Long restaurantId, String ingredientName, Long ingredientCategoryId) {
    Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);

    IngredientCategory category = findIngredientCategoryById(ingredientCategoryId);

    IngredientsItem ingredientsItem = new IngredientsItem();
    ingredientsItem.setName(ingredientName);
    ingredientsItem.setRestaurant(restaurant);
    ingredientsItem.setCategory(category);

    IngredientsItem createdIngredientItem = ingredientItemRepository.save(ingredientsItem);

    category.getIngredientsItems().add(createdIngredientItem);
    return createdIngredientItem;
  }

  @Override
  public List<IngredientsItem> findRestaurantIngredients(Long restaurantId) {
    return ingredientItemRepository.findByRestaurantId(restaurantId);
  }

  @Override
  public IngredientsItem updateStock(Long id) {
    IngredientsItem ingredientsItem =
        ingredientItemRepository
            .findById(id)
            .orElseThrow(IngredientItemNotFoundException::new);

    ingredientsItem.setInStoke(!ingredientsItem.isInStoke());
    return ingredientItemRepository.save(ingredientsItem);
  }
}
