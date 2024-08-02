package com.food.dto.response;

import com.food.dto.RestaurantDto;
import com.food.model.Address;
import com.food.model.Order;
import com.food.model.UserRole;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto {
    private  Long id;
    private String fullName;
    private String email;
    private UserRole role;
    private List<Order> orders;
    private List<RestaurantDto> favorites;
    private List<Address> addresses;
}
