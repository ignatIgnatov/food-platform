package com.food.service;

import com.food.dto.response.UserResponseDto;
import com.food.model.User;

public interface UserService {
  UserResponseDto findUserByJwtToken(String jwt) throws Exception;

  User findUserByEmail(String email) throws Exception;
}
