package com.food.service;

import com.food.dto.request.OrderRequestDto;
import com.food.model.Order;

import java.util.List;

public interface OrderService {

    Order createOrder(OrderRequestDto orderRequestDto, String jwt);

    Order updateOrder(Long orderId, String orderStatus);

    void cancelOrder(Long orderId);

    List<Order> getUserOrders(Long userId);

    List<Order> getRestaurantOrders(Long restaurantId, String orderStatus);

    Order findOrderById(Long orderId);
}