package com.food.dto.response;

import com.food.model.Restaurant;
import lombok.Data;

import java.util.List;

@Data
public class IngredientCategoryResponseDto {
    private Long id;
    private String name;
    private Restaurant restaurant;
    private List<IngredientItemResponseDto> ingredientsItems;
}
