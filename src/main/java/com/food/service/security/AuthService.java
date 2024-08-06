package com.food.service.security;

import com.food.config.jwt.JwtProvider;
import com.food.dto.request.LoginRequestDto;
import com.food.dto.request.UserRequestDto;
import com.food.dto.response.LoginResponseDto;
import com.food.dto.response.UserRegisterResponseDto;
import com.food.exception.user.UserCreateException;
import com.food.exception.user.UserLoginException;
import com.food.model.Cart;
import com.food.model.User;
import com.food.model.UserRole;
import com.food.repository.CartRepository;
import com.food.repository.UserRepository;
import java.util.Collection;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtProvider jwtProvider;
  private final CustomUserDetailsService customUserDetailsService;
  private final CartRepository cartRepository;
  private final MessageSource messageSource;

  public UserRegisterResponseDto createUser(UserRequestDto userRequestDto) {

    User user = userRepository.findByEmail(userRequestDto.getEmail());

    if (user != null) {
      throw new UserCreateException(messageSource, true);
    }

    User createdUser = new User();
    createdUser.setEmail(userRequestDto.getEmail());
    createdUser.setFullName(userRequestDto.getFullName());
    createdUser.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
    createdUser.setRole(UserRole.valueOf(userRequestDto.getRole()));

    User savedUser = userRepository.save(createdUser);

    Cart cart = new Cart();
    cart.setCustomer(savedUser);
    cartRepository.save(cart);

    UserRegisterResponseDto userRegisterResponseDto = new UserRegisterResponseDto();
    userRegisterResponseDto.setMessage("Registration successfully!");

    return userRegisterResponseDto;
  }

  public LoginResponseDto login(LoginRequestDto loginRequestDto) {

    Authentication authentication =
        authenticate(loginRequestDto.getEmail(), loginRequestDto.getPassword());

    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    String role = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();
    String jwt = jwtProvider.generateToken(authentication);

    LoginResponseDto loginResponseDto = new LoginResponseDto();
    loginResponseDto.setJwt(jwt);
    loginResponseDto.setMessage("Login successfully!");
    loginResponseDto.setRole(role);

    return loginResponseDto;
  }

  private Authentication authenticate(String email, String password) {

    UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

    if (userDetails == null) {
      throw new UserLoginException(messageSource);
    }

    if (!passwordEncoder.matches(password, userDetails.getPassword())) {
      throw new UserLoginException(messageSource);
    }

    return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
  }
}
