package com.food.exception.order;

import com.food.exception.common.BadRequestException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class OrderStatusException extends BadRequestException {
    public OrderStatusException(MessageSource messageSource) {
        super(messageSource.getMessage("invalid.order.status", null, LocaleContextHolder.getLocale()));
    }
}
