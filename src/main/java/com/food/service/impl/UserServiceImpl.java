package com.food.service.impl;

import com.food.config.jwt.JwtService;
import com.food.dto.request.UserRequestDto;
import com.food.dto.response.UserResponseDto;
import com.food.exception.user.UserCreateException;
import com.food.exception.user.UserNotFoundException;
import com.food.model.Cart;
import com.food.model.User;
import com.food.repository.CartRepository;
import com.food.repository.UserRepository;
import com.food.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final MessageSource messageSource;
    private final PasswordEncoder passwordEncoder;
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserResponseDto findUserByJwtToken(String jwt) {
        String email = jwtService.extractUsername(jwt.substring(7));
        User user = findUserByEmail(email);
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(messageSource));
    }

    @Override
    public User createUser(UserRequestDto userRequestDto) {

        User user = userRepository.findByEmail(userRequestDto.getEmail()).orElse(null);
        if (user != null) {
            throw new UserCreateException(messageSource, true);
        }

        User createdUser = modelMapper.map(userRequestDto, User.class);
        createdUser.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        userRepository.save(createdUser);

        Cart cart = new Cart();
        cart.setCustomer(createdUser);
        cartRepository.save(cart);

        return createdUser;
    }
}
