package com.food.dto.response;

import com.food.model.IngredientCategory;
import com.food.model.Restaurant;
import lombok.Data;

@Data
public class IngredientItemResponseDto {
    private Long id;
    private String name;
    private IngredientCategory category;
    private Restaurant restaurant;
    private boolean inStoke;
}
