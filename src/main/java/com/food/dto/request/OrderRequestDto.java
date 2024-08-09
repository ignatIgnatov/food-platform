package com.food.dto.request;

import com.food.model.Address;
import lombok.Data;

@Data
public class OrderRequestDto {
    private Long restaurantId;
    private Address deliveryAddress;

}
