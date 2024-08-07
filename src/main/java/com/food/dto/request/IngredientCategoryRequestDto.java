package com.food.dto.request;

import lombok.Data;

@Data
public class IngredientCategoryRequestDto {
    private String name;
    private Long restaurantId;
}
