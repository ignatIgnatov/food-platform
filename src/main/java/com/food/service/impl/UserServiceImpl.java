package com.food.service.impl;

import com.food.config.jwt.JwtProvider;
import com.food.dto.response.UserResponseDto;
import com.food.exception.user.UserNotFoundException;
import com.food.model.User;
import com.food.repository.UserRepository;
import com.food.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final JwtProvider jwtProvider;
  private final MessageSource messageSource;

  @Override
  public UserResponseDto findUserByJwtToken(String jwt) {

    String email = jwtProvider.extractEmailFromToken(jwt);

    User user = findUserByEmail(email);
    return UserResponseDto.builder()
        .id(user.getId())
        .fullName(user.getFullName())
        .email(user.getEmail())
        .role(user.getRole())
        .addresses(user.getAddresses())
        .favorites(user.getFavorites())
        .orders(user.getOrders())
        .build();
  }

  @Override
  public User findUserByEmail(String email) {

    User user = userRepository.findByEmail(email);

    if (user == null) {
      throw new UserNotFoundException(messageSource);
    }

    return user;
  }
}
