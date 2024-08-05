package com.food.service;

import com.food.exception.user.UserNotFoundException;
import com.food.model.User;
import com.food.model.UserRole;
import com.food.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;
  private final MessageSource messageSource;

  @Override
  public UserDetails loadUserByUsername(String username) {
    User user = userRepository.findByEmail(username);
    if (user == null) {
      throw new UserNotFoundException(messageSource);
    }

    UserRole role = user.getRole();

    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(role.toString()));

    return new org.springframework.security.core.userdetails.User(
        user.getEmail(), user.getPassword(), authorities);
  }
}
