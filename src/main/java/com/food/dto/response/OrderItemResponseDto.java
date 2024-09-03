package com.food.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class OrderItemResponseDto {
    private Long id;
    private FoodResponseDto food;
    private int quantity;
    private Long totalPrice;
    private List<String> ingredients;
}
