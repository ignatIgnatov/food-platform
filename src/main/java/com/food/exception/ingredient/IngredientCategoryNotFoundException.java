package com.food.exception.ingredient;

import com.food.exception.common.NoSuchElementException;

public class IngredientCategoryNotFoundException extends NoSuchElementException {
    public IngredientCategoryNotFoundException() {
        super("Ingredient category not found!");
    }
}
