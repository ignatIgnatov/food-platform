package com.food.controller;

import com.food.dto.request.LoginRequestDto;
import com.food.dto.request.UserRequestDto;
import com.food.dto.response.LoginResponseDto;
import com.food.dto.response.MessageResponse;
import com.food.service.security.AuthServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthServiceImpl authServiceImpl;

  @PostMapping("/sign-up")
  public ResponseEntity<MessageResponse> createUser(
      @RequestBody @Valid UserRequestDto userRequestDto) {
    MessageResponse messageResponse = authServiceImpl.register(userRequestDto);
    return new ResponseEntity<>(messageResponse, HttpStatus.CREATED);
  }

  @PostMapping("/sign-in")
  public ResponseEntity<LoginResponseDto> login(
      @RequestBody @Valid LoginRequestDto loginRequestDto) {
    LoginResponseDto loginResponseDto = authServiceImpl.login(loginRequestDto);
    return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
  }
}
