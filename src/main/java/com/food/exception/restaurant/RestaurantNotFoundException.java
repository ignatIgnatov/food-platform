package com.food.exception.restaurant;

import com.food.exception.common.NoSuchElementException;

public class RestaurantNotFoundException extends NoSuchElementException {
    public RestaurantNotFoundException() {
        super("Restaurant not found!");
    }

    public RestaurantNotFoundException(Long userId) {
        super("Restaurant not found! For owner with id: " + userId);
    }
}
