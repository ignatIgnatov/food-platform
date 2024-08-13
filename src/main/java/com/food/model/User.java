package com.food.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.food.dto.response.RestaurantResponseDto;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String fullName;
  private String email;
  private String password;
  private UserRole role = UserRole.ROLE_CUSTOMER;

  @JsonIgnore
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
  private List<Order> orders = new ArrayList<>();

  @ElementCollection private List<RestaurantResponseDto> favorites = new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Address> addresses = new ArrayList<>();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return role.getAuthorities();
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
