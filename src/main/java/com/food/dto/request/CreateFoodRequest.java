package com.food.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class CreateFoodRequest {
    private String name;
    private String description;
    private Long price;
    private FoodCategoryRequestDto foodCategory;
    private List<String> images;
    private Long restaurantId;
    private boolean vegetarian;
    private boolean seasonal;
    private List<FoodIngredientsRequestDto> ingredientsItems;

}
