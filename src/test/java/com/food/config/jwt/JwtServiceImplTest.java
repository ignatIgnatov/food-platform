package com.food.config.jwt;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceImplTest {

    @InjectMocks
    private JwtServiceImpl jwtService;

    private String secretKey;
    private long jwtExpiration;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        secretKey = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
        jwtExpiration = 86400000;
        jwtService = new JwtServiceImpl(secretKey, jwtExpiration);
    }

    @Test
    void testExtractClaim() {
        String token = jwtService.generateToken(createUserDetails());
        Claims claims = jwtService.extractAllClaims(token);
        assertNotNull(claims);
        assertEquals("testUser", claims.getSubject());
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
        assertEquals("testUser", username);
    }

    private UserDetails createUserDetails() {
        return User.withUsername("testUser").password("password").roles("USER").build();
    }
}
