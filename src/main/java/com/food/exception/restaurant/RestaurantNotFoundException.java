package com.food.exception.restaurant;

import com.food.exception.common.NoSuchElementException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class RestaurantNotFoundException extends NoSuchElementException {
    public RestaurantNotFoundException(MessageSource messageSource) {
        super(messageSource.getMessage("restaurant.not.found", null, LocaleContextHolder.getLocale()));
    }

    public RestaurantNotFoundException(MessageSource messageSource, Long userId) {
        super(messageSource.getMessage("restaurant.not.found", null, LocaleContextHolder.getLocale()) + "For owner with id: " + userId);
    }
}
