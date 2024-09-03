package com.food.dto.response;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderResponseDto {

    private Long id;
    private UserResponseDto customer;
    private RestaurantResponseDto restaurant;
    private Long totalAmount;
    private String orderStatus;
    private Date createdDate;
    private AddressResponseDto deliveryAddress;
    private List<OrderItemResponseDto> items;
    private int totalItems;
    private Long totalPrice;
}
