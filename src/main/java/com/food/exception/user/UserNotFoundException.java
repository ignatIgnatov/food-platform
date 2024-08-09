package com.food.exception.user;

import com.food.exception.common.NoSuchElementException;

/** Exception indicating that the user is not found. */
public class UserNotFoundException extends NoSuchElementException {
  public UserNotFoundException() {
    super("User not found!");
  }
}
