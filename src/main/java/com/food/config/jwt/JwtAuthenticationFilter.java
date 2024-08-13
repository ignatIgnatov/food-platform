package com.food.config.jwt;

import com.food.exception.jwt.InvalidTokenException;
import com.food.exception.jwt.JwtExpiredException;
import com.food.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  public static final String JWT_HEADER = "Authorization";
  public static final String JWT_PREFIX = "Bearer ";
  public static final String USER_KEY = "user";
  public static final String AUTH_PATH = "/auth";

  private final JwtService jwtService;
  private final UserService userService;
  private final MessageSource messageSource;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    if (request.getServletPath().contains(AUTH_PATH)) {
      filterChain.doFilter(request, response);
      return;
    }

    request.setAttribute(USER_KEY, null);

    final String authHeader = request.getHeader(JWT_HEADER);

    if (authHeader == null || !authHeader.startsWith(JWT_PREFIX)) {
      filterChain.doFilter(request, response);
      throw new InvalidTokenException(messageSource);
    }

    final String jwt = authHeader.substring(7);
    final String userEmail = jwtService.extractUsername(jwt);

    if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = userService.findUserByEmail(userEmail);

      if (jwtService.isTokenValid(jwt, userDetails)) {
        UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authToken);
      } else {
        filterChain.doFilter(request, response);
        throw new JwtExpiredException(messageSource);
      }

      request.setAttribute(USER_KEY, userDetails);
    }
    filterChain.doFilter(request, response);
  }
}
