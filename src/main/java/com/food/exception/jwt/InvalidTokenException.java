package com.food.exception.jwt;

import com.food.exception.common.UnauthorizedException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * Exception thrown when the provided authentication token is invalid or expired (so the request
 * cannot be authorized). Sets the appropriate message using MessageSource (the messages are in
 * src/main/resources/messages).
 */
public class InvalidTokenException extends UnauthorizedException {
  public InvalidTokenException(MessageSource messageSource) {
    super(messageSource.getMessage("token.expired", null, LocaleContextHolder.getLocale()));
  }
}
