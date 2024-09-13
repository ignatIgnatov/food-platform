package com.food.dto.response;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Embeddable
public class RestaurantResponseDto {
    private Long id;
    private UserResponseDto owner;
    private String name;
    private String description;
    private String cuisineType;
    private AddressResponseDto address;
    private String openingHours;
    @Column(length = 1000)
    private List<String> images;
    private LocalDateTime registrationDate;
    private boolean open;
    private ContactResponseDto contactInformation;
}
