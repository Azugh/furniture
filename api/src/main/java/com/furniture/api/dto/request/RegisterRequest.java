package com.furniture.api.dto.request;

import com.furniture.core.model.User.Role;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  @NotBlank(message = "First name is required")
  private String firstName;

  @NotBlank(message = "Last name is required")
  private String lastName;

  @NotBlank(message = "Email is required")
  @Email(message = "Email should be valid")
  private String email;

  @NotBlank(message = "Phone is required")
  @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number should be valid")
  private String phone;

  @NotBlank(message = "Password is required")
  @Size(min = 6, message = "Password should be at least 6 characters")
  private String password;

  private Role role;
}
