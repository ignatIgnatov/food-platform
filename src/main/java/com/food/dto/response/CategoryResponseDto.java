package com.food.dto.response;

import lombok.Data;

@Data
public class CategoryResponseDto {

    private Long id;
    private String name;
    private RestaurantResponseDto restaurant;
}
