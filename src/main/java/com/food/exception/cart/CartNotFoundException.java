package com.food.exception.cart;

import com.food.exception.common.NoSuchElementException;

public class CartNotFoundException extends NoSuchElementException {
    public CartNotFoundException() {
        super("Cart not found!");
    }
}
