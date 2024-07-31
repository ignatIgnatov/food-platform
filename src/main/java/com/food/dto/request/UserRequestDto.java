package com.food.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequestDto {

  private static final String PWD_VALIDATION_MSG =
      "Password must contain at least one digit, one lowercase letter and one uppercase letter!";

  @NotBlank(message = "Name is required!")
  private String fullName;

  @Email private String email;

  @NotBlank(message = "Password is required!")
  @Size(min = 8, message = "Password must be at least 8 characters!")
  @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$", message = PWD_VALIDATION_MSG)
  private String password;

  @NotBlank(message = "Role is required!")
  private String role;
}
