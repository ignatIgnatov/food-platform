package com.food.service;

import com.food.dto.response.CategoryResponseDto;
import com.food.model.Category;

import java.util.List;

public interface CategoryService {
    CategoryResponseDto createCategory(String name, Long userId);

    List<CategoryResponseDto> findCategoryByRestaurantId(Long restaurantId);

    Category findCategoryById(Long id);
}
