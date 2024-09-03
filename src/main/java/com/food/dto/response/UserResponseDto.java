package com.food.dto.response;

import com.food.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {
    private Long id;
    private String fullName;
    private String email;
    private UserRole role;
    private List<CreateRestaurantResponseDto> favorites;
    private List<AddressResponseDto> addresses;
}
