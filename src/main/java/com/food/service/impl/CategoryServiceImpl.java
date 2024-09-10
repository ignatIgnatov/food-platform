package com.food.service.impl;

import com.food.dto.response.CategoryResponseDto;
import com.food.dto.response.RestaurantResponseDto;
import com.food.exception.category.CategoryNotFoundException;
import com.food.model.Category;
import com.food.model.Restaurant;
import com.food.repository.CategoryRepository;
import com.food.service.CategoryService;
import com.food.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final RestaurantService restaurantService;
    private final MessageSource messageSource;
    private final ModelMapper modelMapper;

    @Override
    public CategoryResponseDto createCategory(String name, Long userId) {
        RestaurantResponseDto restaurantResponseDto = restaurantService.getRestaurantByUserId(userId);
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantResponseDto.getId());
        Category category = new Category();
        category.setName(name);
        category.setRestaurant(restaurant);
        categoryRepository.save(category);
        return modelMapper.map(category, CategoryResponseDto.class);
    }

    @Override
    public List<CategoryResponseDto> findCategoryByRestaurantId(Long restaurantId) {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        List<Category> categories = categoryRepository.findByRestaurantId(restaurant.getId());
        return categories.stream()
                .map(category -> modelMapper.map(category, CategoryResponseDto.class))
                .toList();
    }

    @Override
    public Category findCategoryById(Long id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(messageSource));
    }
}
