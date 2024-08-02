package com.food.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.crypto.SecretKey;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class JwtProvider {

  private final SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

  public String generateToken(Authentication authentication) {

    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    String roles = populateAuthorities(authorities);

    return Jwts.builder()
        .setIssuedAt(new Date())
        .setExpiration(new Date(new Date().getTime() + 840000))
        .claim("email", authentication.getName())
        .claim("authorities", roles)
        .signWith(key)
        .compact();
  }

  public String extractEmailFromToken(String jwt) {
    return String.valueOf(getClaims(jwt).get("email"));
  }

  private Claims getClaims(String jwt) {
    jwt = jwt.substring(7);
    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
  }

  private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
    Set<String> auths = new HashSet<>();

    for (GrantedAuthority authority : authorities) {
      auths.add(authority.getAuthority());
    }

    return String.join(",", auths);
  }
}
