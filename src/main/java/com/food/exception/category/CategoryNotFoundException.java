package com.food.exception.category;

import com.food.exception.common.NoSuchElementException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class CategoryNotFoundException extends NoSuchElementException {
    public CategoryNotFoundException(MessageSource messageSource) {
        super(messageSource.getMessage("category.not.found", null, LocaleContextHolder.getLocale()));
    }
}
