package com.food.config.jwt;

import static org.junit.jupiter.api.Assertions.*;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

@ExtendWith(MockitoExtension.class)
class JwtServiceImplTest {

  @InjectMocks private JwtServiceImpl jwtService;

  private String secretKey;
  private long jwtExpiration;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    secretKey = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    jwtExpiration = 86400000;
    jwtService = new JwtServiceImpl();
  }

  @Test
  void testExtractClaim() {
    String token = jwtService.generateToken(createUserDetails());
    Claims claims = jwtService.extractAllClaims(token);
    assertNotNull(claims);
    assertEquals("testuser", claims.getSubject());
  }

  @Test
  void testIsTokenValidWithValidToken() {
    String token = jwtService.generateToken(createUserDetails());
    assertTrue(jwtService.isTokenValid(token, createUserDetails()));
  }

  @Test
  void testGenerateToken() {
    String token = jwtService.generateToken(createUserDetails());
    assertNotNull(token);
  }

  @Test
  void testExtractUsername() {
    String token = jwtService.generateToken(createUserDetails());
    String username = jwtService.extractUsername(token);
    assertEquals("testuser", username);
  }

  private UserDetails createUserDetails() {
    return User.withUsername("testuser").password("password").roles("USER").build();
  }
}
