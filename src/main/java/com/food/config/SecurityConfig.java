package com.food.config;

import com.food.config.jwt.JwtAuthenticationFilter;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  public static final String AUTH_HEADER = "Authorization";
  public static final String BASE_URL = "http://localhost:3000";

  private final AuthenticationProvider authenticationProvider;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http.sessionManagement(
            management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers("/api/admin/**")
                    .hasAnyRole("RESTAURANT_OWNER", "ADMIN")
                    .requestMatchers("/api/**")
                    .authenticated()
                    .anyRequest()
                    .permitAll())
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .csrf(AbstractHttpConfigurer::disable)
        .cors(cors -> cors.configurationSource(configurationSource()));

    return http.build();
  }

  private CorsConfigurationSource configurationSource() {
    return request -> {
      CorsConfiguration corsConfiguration = new CorsConfiguration();
      corsConfiguration.setAllowedOrigins(List.of(BASE_URL));
      corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
      corsConfiguration.setAllowCredentials(true);
      corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
      corsConfiguration.setExposedHeaders(List.of(AUTH_HEADER));
      corsConfiguration.setMaxAge(3600L);
      return corsConfiguration;
    };
  }
}
