package com.food.service.impl;

import com.food.dto.request.CreateFoodRequest;
import com.food.exception.food.FoodNotFoundException;
import com.food.model.Category;
import com.food.model.Food;
import com.food.model.Restaurant;
import com.food.repository.FoodRepository;
import com.food.service.FoodService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

  private final FoodRepository foodRepository;

  @Override
  public Food createFood(
      CreateFoodRequest createFoodRequest, Category category, Restaurant restaurant) {

    Food food = new Food();
    food.setFoodCategory(category);
    food.setRestaurant(restaurant);
    food.setDescription(createFoodRequest.getDescription());
    food.setImages(createFoodRequest.getImages());
    food.setName(createFoodRequest.getName());
    food.setPrice(createFoodRequest.getPrice());
    food.setIngredientsItems(createFoodRequest.getIngredients());
    food.setSeasonal(createFoodRequest.isSeasonal());
    food.setVegetarian(createFoodRequest.isVegetarian());

    Food createdFood = foodRepository.save(food);

    restaurant.getFoods().add(createdFood);

    return createdFood;
  }

  @Override
  public void deleteFood(Long foodId) {
    Food food = findFoodById(foodId);
    food.setRestaurant(null);
    foodRepository.save(food);
  }

  @Override
  public List<Food> getRestaurantsFood(
      Long restaurantId,
      boolean isVegetarian,
      boolean isNonVeg,
      boolean isSeasonal,
      String foodCategory) {

    List<Food> foods = foodRepository.findByRestaurantId(restaurantId);

    if (isVegetarian) {
      return filterByVegetarian(foods);
    }

    if (isNonVeg) {
      return filterByNonVeg(foods);
    }

    if (isSeasonal) {
      return filterBySeason(foods);
    }

    if (foodCategory != null && !foodCategory.trim().isEmpty()) {
      return filterByCategory(foods, foodCategory);
    }

    return foods;
  }

  @Override
  public List<Food> searchFood(String keyword) {
    return foodRepository.searchFood(keyword);
  }

  @Override
  public Food findFoodById(Long foodId) {
    return foodRepository
        .findById(foodId)
        .orElseThrow(FoodNotFoundException::new);
  }

  @Override
  public Food updateAvailabilityStatus(Long foodId) {
    Food food = findFoodById(foodId);
    food.setAvailable(!food.isAvailable());
    return foodRepository.save(food);
  }

  private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
    return foods.stream()
        .filter(
            food -> {
              if (food.getFoodCategory() != null) {
                return food.getFoodCategory().getName().equals(foodCategory);
              }
              return false;
            })
        .toList();
  }

  private List<Food> filterBySeason(List<Food> foods) {
    return foods.stream().filter(Food::isSeasonal).toList();
  }

  private List<Food> filterByNonVeg(List<Food> foods) {
    return foods.stream().filter(food -> !food.isVegetarian()).toList();
  }

  private List<Food> filterByVegetarian(List<Food> foods) {
    return foods.stream().filter(Food::isVegetarian).toList();
  }
}
