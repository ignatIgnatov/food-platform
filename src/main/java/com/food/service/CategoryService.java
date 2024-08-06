package com.food.service;

import com.food.model.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(String name, Long userId);

    List<Category> findCategoryByRestaurantId(Long userId);

    Category findCategoryById(Long id);
}
