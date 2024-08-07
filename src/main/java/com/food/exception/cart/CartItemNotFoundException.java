package com.food.exception.cart;

import com.food.exception.common.NoSuchElementException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class CartItemNotFoundException extends NoSuchElementException {
    public CartItemNotFoundException(MessageSource messageSource) {
        super(messageSource.getMessage("cart.item.not.found", null, LocaleContextHolder.getLocale()));
    }
}
