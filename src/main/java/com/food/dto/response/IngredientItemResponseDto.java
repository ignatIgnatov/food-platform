package com.food.dto.response;

import lombok.Data;

@Data
public class IngredientItemResponseDto {
    private Long id;
    private String name;
    private IngredientCategoryResponseDto category;
    private boolean inStoke;
}
