package com.food.service.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.food.config.jwt.JwtServiceImpl;
import com.food.dto.request.LoginRequestDto;
import com.food.dto.request.UserRequestDto;
import com.food.dto.response.LoginResponseDto;
import com.food.dto.response.MessageResponse;
import com.food.exception.user.UserLoginException;
import com.food.model.User;
import com.food.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

class AuthServiceImplTest {

  @Mock private MessageSource messageSource;

  @Mock private PasswordEncoder passwordEncoder;

  @Mock private UserServiceImpl userService;

  @Mock private JwtServiceImpl jwtService;

  @Mock private CustomUserDetailsService customUserDetailsService;

  @Mock private AuthenticationManager authenticationManager;

  private AuthService authService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    authService =
        new AuthServiceImpl(
            userService, passwordEncoder, jwtService, customUserDetailsService, messageSource);
  }

  @Test
  void testRegister() {
    UserRequestDto registerRequest = new UserRequestDto();
    User user = new User();

    when(userService.createUser(any(UserRequestDto.class))).thenReturn(user);
    when(jwtService.generateToken(user)).thenReturn("mockedAccessToken");

    MessageResponse response = authService.register(registerRequest);

    Assertions.assertNotNull(response);
    assertEquals("Registration successfully!", response.getMessage());
  }

  @Test
  void testLogin() {
    LoginRequestDto loginRequestDto = new LoginRequestDto();
    loginRequestDto.setEmail("test@email.com");
    loginRequestDto.setPassword("password");

    UserDetails userDetails = new User();

    when(customUserDetailsService.loadUserByUsername(loginRequestDto.getEmail()))
        .thenReturn(userDetails);
    when(passwordEncoder.matches(loginRequestDto.getPassword(), userDetails.getPassword()))
        .thenReturn(true);

    Authentication authentication =
        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    when(authenticationManager.authenticate(authentication)).thenReturn(authentication);
    when(jwtService.generateToken(userDetails)).thenReturn("mockedAccessToken");

    LoginResponseDto response = authService.login(loginRequestDto);

    Assertions.assertNotNull(response);

    verify(customUserDetailsService, times(1)).loadUserByUsername(loginRequestDto.getEmail());
  }

  @Test
  void testAuthenticateWithUserNotFoundException() {
    LoginRequestDto request = new LoginRequestDto();
    request.setEmail("nonexistent@example.com");
    request.setPassword("password");

    assertThrows(UserLoginException.class, () -> authService.login(request));
  }

  @Test
  void testAuthenticateShouldThrowWhenPasswordNotMatch() {
    LoginRequestDto loginRequestDto = new LoginRequestDto();
    loginRequestDto.setEmail("test@email.com");
    loginRequestDto.setPassword("wrongPassword");

    UserDetails userDetails = new User();
    when(customUserDetailsService.loadUserByUsername(loginRequestDto.getEmail()))
        .thenReturn(userDetails);
    when(passwordEncoder.matches(loginRequestDto.getPassword(), userDetails.getPassword()))
        .thenReturn(false);

    assertThrows(UserLoginException.class, () -> authService.login(loginRequestDto));
  }
}
