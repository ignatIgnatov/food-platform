package com.food.exception.cart;

import com.food.exception.common.NoSuchElementException;

public class CartItemNotFoundException extends NoSuchElementException {
    public CartItemNotFoundException() {
        super("Cart item not found!");
    }
}
