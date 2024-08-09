package com.food.exception.user;

import com.food.exception.common.BadRequestException;

/** Exception thrown when there is an issue with user login, such as invalid email or password. */
public class UserLoginException extends BadRequestException {
  public UserLoginException() {
    super("Invalid email or password!");
  }
}
