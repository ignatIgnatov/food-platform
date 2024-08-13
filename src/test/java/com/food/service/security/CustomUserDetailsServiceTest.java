package com.food.service.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.food.exception.user.UserNotFoundException;
import com.food.model.User;
import com.food.model.UserRole;
import com.food.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UserDetails;

class CustomUserDetailsServiceTest {

  @Mock private UserRepository userRepository;

  @Mock private MessageSource messageSource;

  @InjectMocks private CustomUserDetailsService customUserDetailsService;

  private User user;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    user = new User();
    user.setEmail("test@mail.com");
    user.setPassword("password");
    user.setRole(UserRole.ROLE_ADMIN);
  }

  @Test
  void testLoadUserByUsernameSuccessfully() {

    when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

    UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());

    assertNotNull(userDetails);

    verify(userRepository, times(1)).findByEmail(user.getEmail());
  }

  @Test
  void testLoadUserByUsernameShouldThrowWhenUserNotFound() {
    assertThrows(
        UserNotFoundException.class,
        () -> customUserDetailsService.loadUserByUsername(user.getEmail()));
  }
}
