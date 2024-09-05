package com.food.service;

import com.food.dto.request.OrderRequestDto;
import com.food.dto.response.OrderResponseDto;
import com.food.model.Order;

import java.util.List;

public interface OrderService {

    OrderResponseDto createOrder(OrderRequestDto orderRequestDto, String jwt);

    OrderResponseDto updateOrder(Long orderId, String orderStatus);

    void cancelOrder(Long orderId);

    List<OrderResponseDto> getUserOrders(Long userId);

    List<Order> getRestaurantOrders(Long restaurantId, String orderStatus);

    Order findOrderById(Long orderId);
}
