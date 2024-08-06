package com.food.service;

import com.food.dto.request.UserRequestDto;
import com.food.dto.response.UserResponseDto;
import com.food.model.User;

public interface UserService {
  UserResponseDto findUserByJwtToken(String jwt);

  User findUserByEmail(String email);

  User createUser(UserRequestDto userRequestDto);
}
