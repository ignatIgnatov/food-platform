package com.food.controller;

import com.food.dto.request.LoginRequestDto;
import com.food.dto.response.LoginResponseDto;
import com.food.dto.request.UserRequestDto;
import com.food.dto.response.UserResponseDto;
import com.food.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/sign-up")
  public ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid UserRequestDto userRequestDto)
      throws Exception {
    return authService.createUser(userRequestDto);
  }

  @PostMapping("/sign-in")
  public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
    return authService.login(loginRequestDto);
  }
}