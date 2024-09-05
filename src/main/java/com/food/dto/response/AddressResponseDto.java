package com.food.dto.response;

import lombok.Data;

@Data
public class AddressResponseDto {
    private Long id;
    private String streetAddress;
    private String city;
    private String stateProvince;
    private String postalCode;
    private String country;
}
