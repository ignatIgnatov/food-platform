package com.food.exception.ingredient;

import com.food.exception.common.NoSuchElementException;

public class IngredientItemNotFoundException extends NoSuchElementException {
    public IngredientItemNotFoundException() {
        super("Ingredient item not found!");
    }
}
