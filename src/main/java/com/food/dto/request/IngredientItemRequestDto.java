package com.food.dto.request;

import lombok.Data;

@Data
public class IngredientItemRequestDto {
    private String name;
    private Long categoryId;
    private Long restaurantId;
}
