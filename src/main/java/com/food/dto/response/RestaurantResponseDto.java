package com.food.dto.response;

import com.food.model.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestaurantResponseDto {
    private Long id;
    private User owner;
    private String name;
    private String description;
    private String cuisineType;
    private Address address;
    private ContactInformation contactInformation;
    private String openingHours;
    private List<Order> orders;
    private List<String> images;
    private LocalDateTime registrationDate;
    private boolean open;
    private List<Food> foods;
}
