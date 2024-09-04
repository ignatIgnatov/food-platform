package com.food.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class CartItemResponseDto {
    private Long id;
    private int quantity;
    private List<String> ingredients;
    private Long totalPrice;
}
