package com.food.dto.request;

import lombok.Data;

@Data
public class FoodIngredientsRequestDto {
    private Long id;
    private String name;
    private FoodIngredientCategoryRequestDto category;
    private boolean inStoke;
}
