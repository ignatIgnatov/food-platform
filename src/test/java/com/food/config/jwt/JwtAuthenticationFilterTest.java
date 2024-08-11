package com.food.config.jwt;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

import com.food.exception.jwt.InvalidTokenException;
import com.food.exception.jwt.JwtExpiredException;
import com.food.model.User;
import com.food.model.UserRole;
import com.food.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class JwtAuthenticationFilterTest {

  @Mock MessageSource messageSource;

  @Mock private JwtService jwtService;

  @Mock private UserService userService;

  @InjectMocks private JwtAuthenticationFilter jwtAuthenticationFilter;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(jwtAuthenticationFilter).build();
  }

  @Test
  void testDoFilterInternal_AuthHeaderNull_ShouldThrow() throws ServletException, IOException {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    FilterChain filterChain = mock(FilterChain.class);

    when(request.getServletPath()).thenReturn("/some-path");
    when(request.getHeader("Authorization")).thenReturn(null);

    assertThrows(InvalidTokenException.class, () -> jwtAuthenticationFilter.doFilterInternal(request, response, filterChain));

    verify(filterChain, times(1)).doFilter(request, response);
  }

  @Test
  void testDoFilterInternal_ExpiredToken_ShouldThrow() throws ServletException, IOException {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    FilterChain filterChain = mock(FilterChain.class);

    when(request.getServletPath()).thenReturn("/some-path");
    when(request.getHeader("Authorization")).thenReturn("Bearer validToken");
    User user = new User();
    user.setRole(UserRole.ROLE_CUSTOMER);
    when(userService.findUserByEmail("user@example.com")).thenReturn(user);
    when(jwtService.extractUsername("validToken")).thenReturn("user@example.com");

    when(jwtService.isTokenValid("validToken", user)).thenReturn(false);

    assertThrows(JwtExpiredException.class, () -> jwtAuthenticationFilter.doFilterInternal(request, response, filterChain));

    verify(filterChain, times(1)).doFilter(request, response);
  }

  @Test
  void testDoFilterInternal_ValidToken_ShouldSetAuthentication() throws ServletException, IOException {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    FilterChain filterChain = mock(FilterChain.class);

    when(request.getServletPath()).thenReturn("/api/some-path");
    when(request.getHeader("Authorization")).thenReturn("Bearer validToken");
    when(jwtService.extractUsername("validToken")).thenReturn("user@example.com");

    User user = new User();
    user.setRole(UserRole.ROLE_CUSTOMER);
    when(userService.findUserByEmail("user@example.com")).thenReturn(user);

    when(jwtService.isTokenValid("validToken", user)).thenReturn(true);

    jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

    verify(filterChain, times(1)).doFilter(request, response);
  }

  @Test
  void testDoFilterInternal_AuthHeaderNotStartsWithBearer_ShouldThrow() throws ServletException, IOException {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    FilterChain filterChain = mock(FilterChain.class);

    when(request.getServletPath()).thenReturn("/some-path");
    when(request.getHeader("Authorization")).thenReturn("InvalidToken");

    assertThrows(InvalidTokenException.class, () -> jwtAuthenticationFilter.doFilterInternal(request, response, filterChain));

    verify(filterChain, times(1)).doFilter(request, response);
  }

  @Test
  void testDoFilterInternal_InvalidToken_ShouldThrow() throws ServletException, IOException {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    FilterChain filterChain = mock(FilterChain.class);

    when(request.getServletPath()).thenReturn("/some-path");
    when(request.getHeader("Authorization")).thenReturn("Bearer invalidToken");
    when(jwtService.extractUsername("invalidToken")).thenReturn("user@example.com");
    when(userService.findUserByEmail("user@example.com")).thenReturn(mock(User.class));

    assertThrows(JwtExpiredException.class, () -> jwtAuthenticationFilter.doFilterInternal(request, response, filterChain));

    verify(filterChain, times(1)).doFilter(request, response);
  }
}
