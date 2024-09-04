package com.food.dto.response;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FoodResponseDto {
    private Long id;
    private String name;
    private String description;
    private Long price;
    private CategoryResponseDto foodCategory;
    private List<String> images;
    private boolean available;
    private boolean isVegetarian;
    private boolean isSeasonal;
    private List<IngredientItemResponseDto> ingredientsItems;
    private Date creationDate;
}
