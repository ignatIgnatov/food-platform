package com.food.exception.cart;

import com.food.exception.common.NoSuchElementException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class CartNotFoundException extends NoSuchElementException {
    public CartNotFoundException(MessageSource messageSource) {
        super(messageSource.getMessage("cart.not.found", null, LocaleContextHolder.getLocale()));
    }
}
