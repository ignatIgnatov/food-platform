package com.food.exception.order;

import com.food.exception.common.BadRequestException;

public class OrderStatusException extends BadRequestException {
  public OrderStatusException() {
    super("Invalid order status!");
  }
}
