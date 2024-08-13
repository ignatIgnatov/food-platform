package com.food.service.security;

import com.food.dto.request.LoginRequestDto;
import com.food.dto.request.UserRequestDto;
import com.food.dto.response.LoginResponseDto;
import com.food.dto.response.MessageResponse;

public interface AuthService {

  MessageResponse register(UserRequestDto userRequestDto);

  LoginResponseDto login(LoginRequestDto loginRequestDto);
}
