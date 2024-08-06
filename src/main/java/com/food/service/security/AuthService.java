package com.food.service.security;

import com.food.config.jwt.JwtProvider;
import com.food.dto.request.LoginRequestDto;
import com.food.dto.request.UserRequestDto;
import com.food.dto.response.LoginResponseDto;
import com.food.dto.response.MessageResponse;
import com.food.exception.user.UserLoginException;
import com.food.service.UserService;
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

  private final UserService userService;
  private final PasswordEncoder passwordEncoder;
  private final JwtProvider jwtProvider;
  private final CustomUserDetailsService customUserDetailsService;
  private final MessageSource messageSource;

  public MessageResponse createUser(UserRequestDto userRequestDto) {

    userService.createUser(userRequestDto);
    MessageResponse messageResponse = new MessageResponse();
    messageResponse.setMessage("Registration successfully!");
    return messageResponse;
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
