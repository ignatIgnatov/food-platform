package com.food.service.impl;

import com.food.config.jwt.JwtProvider;
import com.food.dto.request.UserRequestDto;
import com.food.dto.response.UserResponseDto;
import com.food.exception.user.UserCreateException;
import com.food.exception.user.UserNotFoundException;
import com.food.model.Cart;
import com.food.model.User;
import com.food.model.UserRole;
import com.food.repository.CartRepository;
import com.food.repository.UserRepository;
import com.food.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final JwtProvider jwtProvider;
  private final PasswordEncoder passwordEncoder;
  private final CartRepository cartRepository;

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
      throw new UserNotFoundException();
    }

    return user;
  }

  @Override
  public User createUser(UserRequestDto userRequestDto) {

    User user = userRepository.findByEmail(userRequestDto.getEmail());

    if (user != null) {
      throw new UserCreateException(true);
    }

    User createdUser = new User();
    createdUser.setEmail(userRequestDto.getEmail());
    createdUser.setFullName(userRequestDto.getFullName());
    createdUser.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
    createdUser.setRole(UserRole.valueOf(userRequestDto.getRole()));

    Cart cart = new Cart();
    cart.setCustomer(createdUser);
    cartRepository.save(cart);

    return userRepository.save(createdUser);
  }
}
