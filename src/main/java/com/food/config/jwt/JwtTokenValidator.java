package com.food.config.jwt;

import com.food.exception.common.BadRequestException;
import com.food.exception.jwt.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtTokenValidator extends OncePerRequestFilter {

  private final SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

  private final MessageSource messageSource;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String jwt = request.getHeader(JwtConstant.JWT_HEADER);

    if (jwt != null) {
      jwt = jwt.substring(7);
      Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

      try {
        String email = String.valueOf(claims.get("email"));
        String authorities = String.valueOf(claims.get("authorities"));

        List<GrantedAuthority> auth =
            AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, auth);
        SecurityContextHolder.getContext().setAuthentication(authentication);

      } catch (BadRequestException e) {
        throw new InvalidTokenException(messageSource);
      }
    }

    filterChain.doFilter(request, response);
  }
}
