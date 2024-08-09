package com.food.exception.order;

import com.food.exception.common.NoSuchElementException;

public class OrderNotFoundException extends NoSuchElementException {
  public OrderNotFoundException() {
    super("Order not found!");
  }
}
