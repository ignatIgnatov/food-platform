package com.food.dto.request;

import com.food.model.*;
import java.util.List;
import lombok.Data;

@Data
public class CreateRestaurantRequestDto {
    private User owner;
    private String name;
    private String description;
    private String cuisineType;
    private Address address;
    private ContactInformation contactInformation;
    private String openingHours;
    private List<String> images;

}
