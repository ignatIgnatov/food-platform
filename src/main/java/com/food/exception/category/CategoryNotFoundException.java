package com.food.exception.category;

import com.food.exception.common.NoSuchElementException;

public class CategoryNotFoundException extends NoSuchElementException {
    public CategoryNotFoundException() {
        super("Category not found!");
    }
}
