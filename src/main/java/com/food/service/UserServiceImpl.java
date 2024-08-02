package com.food.service;

import com.food.config.jwt.JwtProvider;
import com.food.dto.response.UserResponseDto;
import com.food.model.User;
import com.food.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final JwtProvider jwtProvider;

  @Override
  public UserResponseDto findUserByJwtToken(String jwt) throws Exception {

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
  public User findUserByEmail(String email) throws Exception {

    User user = userRepository.findByEmail(email);

    if (user == null) {
      throw new Exception("User not found!");
    }

    return user;
  }
}
