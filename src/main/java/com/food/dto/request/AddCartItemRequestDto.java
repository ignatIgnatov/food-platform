package com.food.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class AddCartItemRequestDto {
    private Long foodId;
    private int quantity;
    private List<String> ingredients;
}
