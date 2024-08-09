package com.food.exception.food;

import com.food.exception.common.NoSuchElementException;

public class FoodNotFoundException extends NoSuchElementException {
  public FoodNotFoundException() {
    super("Food not found!");
  }
}
