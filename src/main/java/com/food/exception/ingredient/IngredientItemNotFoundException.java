package com.food.exception.ingredient;

import com.food.exception.common.NoSuchElementException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class IngredientItemNotFoundException extends NoSuchElementException {
    public IngredientItemNotFoundException(MessageSource messageSource) {
        super(messageSource.getMessage("ingredient.item.not.found", null, LocaleContextHolder.getLocale()));
    }
}
