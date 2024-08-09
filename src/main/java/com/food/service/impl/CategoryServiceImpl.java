package com.food.service.impl;

import com.food.exception.category.CategoryNotFoundException;
import com.food.model.Category;
import com.food.model.Restaurant;
import com.food.repository.CategoryRepository;
import com.food.service.CategoryService;
import com.food.service.RestaurantService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;
  private final RestaurantService restaurantService;
  private final MessageSource messageSource;

  @Override
  public Category createCategory(String name, Long userId) {
    Restaurant restaurant = restaurantService.getRestaurantByUserId(userId);
    Category category = new Category();
    category.setName(name);
    category.setRestaurant(restaurant);
    return categoryRepository.save(category);
  }

  @Override
  public List<Category> findCategoryByRestaurantId(Long userId) {
    Restaurant restaurant = restaurantService.getRestaurantByUserId(userId);
    return categoryRepository.findByRestaurantId(restaurant.getId());
  }

  @Override
  public Category findCategoryById(Long id) {
    return categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
  }
}
