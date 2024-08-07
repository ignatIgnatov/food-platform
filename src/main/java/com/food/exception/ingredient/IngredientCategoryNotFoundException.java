package com.food.exception.ingredient;

import com.food.exception.common.NoSuchElementException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class IngredientCategoryNotFoundException extends NoSuchElementException {
    public IngredientCategoryNotFoundException(MessageSource messageSource) {
        super(messageSource.getMessage("ingredient.category.not.found", null, LocaleContextHolder.getLocale()));
    }
}
