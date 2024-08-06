package com.food.exception.food;

import com.food.exception.common.NoSuchElementException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class FoodNotFoundException extends NoSuchElementException {
    public FoodNotFoundException(MessageSource messageSource) {
        super(messageSource.getMessage("food.not.found", null, LocaleContextHolder.getLocale()));
    }
}
