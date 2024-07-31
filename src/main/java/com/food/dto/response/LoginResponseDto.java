package com.food.dto.response;

import lombok.Data;

@Data
public class LoginResponseDto {
    private String jwt;
    private String message;
    private String role;
}
