package com.food.exception.jwt;

import com.food.exception.common.UnauthorizedException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class JwtExpiredException extends UnauthorizedException {
    public JwtExpiredException(MessageSource messageSource) {
        super(messageSource.getMessage("token.invalid", null, LocaleContextHolder.getLocale()));
    }
}
