package com.food.exception.order;

import com.food.exception.common.NoSuchElementException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class OrderNotFoundException extends NoSuchElementException {
    public OrderNotFoundException(MessageSource messageSource) {
        super(messageSource.getMessage("order.not.found", null, LocaleContextHolder.getLocale()));
    }
}
