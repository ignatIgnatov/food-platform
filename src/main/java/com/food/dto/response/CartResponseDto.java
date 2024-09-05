package com.food.dto.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CartResponseDto {
    private Long id;
    private UserResponseDto customer;
    private Long total;
    private List<CartItemResponseDto> items = new ArrayList<>();
}
