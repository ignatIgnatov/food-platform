package com.food.service.impl;

import com.food.dto.request.IngredientCategoryRequestDto;
import com.food.dto.response.IngredientCategoryResponseDto;
import com.food.dto.response.IngredientItemResponseDto;
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
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientsService {

  private final IngredientItemRepository ingredientItemRepository;
  private final IngredientCategoryRepository ingredientCategoryRepository;
  private final RestaurantService restaurantService;
  private final MessageSource messageSource;
  private final ModelMapper modelMapper;

  @Override
  public IngredientCategoryResponseDto createIngredientCategory(IngredientCategoryRequestDto request) {
    Restaurant restaurant = restaurantService.findRestaurantById(request.getRestaurantId());

    IngredientCategory ingredientCategory = new IngredientCategory();
    ingredientCategory.setRestaurant(restaurant);
    ingredientCategory.setName(request.getName());
    ingredientCategoryRepository.save(ingredientCategory);

    return modelMapper.map(ingredientCategory, IngredientCategoryResponseDto.class);
  }

  @Override
  public IngredientCategory findIngredientCategoryById(Long id) {
    return ingredientCategoryRepository
        .findById(id)
        .orElseThrow(() -> new IngredientCategoryNotFoundException(messageSource));
  }

  @Override
  public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) {
    restaurantService.findRestaurantById(id);
    return ingredientCategoryRepository.findByRestaurantId(id);
  }

  @Override
  public IngredientItemResponseDto createIngredientItem(
      Long restaurantId, String ingredientName, Long ingredientCategoryId) {
    Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);

    IngredientCategory category = findIngredientCategoryById(ingredientCategoryId);

    IngredientsItem ingredientsItem = new IngredientsItem();
    ingredientsItem.setName(ingredientName);
    ingredientsItem.setRestaurant(restaurant);
    ingredientsItem.setCategory(category);

    IngredientsItem createdIngredientItem = ingredientItemRepository.save(ingredientsItem);

    category.getIngredientsItems().add(createdIngredientItem);
    return modelMapper.map(createdIngredientItem, IngredientItemResponseDto.class);
  }

  @Override
  public List<IngredientItemResponseDto> findRestaurantIngredients(Long restaurantId) {
    List<IngredientsItem> ingredientsItems = ingredientItemRepository.findByRestaurantId(restaurantId);
    return ingredientsItems.stream()
            .map(ingredient -> modelMapper.map(ingredient, IngredientItemResponseDto.class))
            .toList();
  }

  @Override
  public IngredientsItem updateStock(Long id) {
    IngredientsItem ingredientsItem =
        ingredientItemRepository
            .findById(id)
            .orElseThrow(() -> new IngredientItemNotFoundException(messageSource));

    ingredientsItem.setInStoke(!ingredientsItem.isInStoke());
    return ingredientItemRepository.save(ingredientsItem);
  }
}
