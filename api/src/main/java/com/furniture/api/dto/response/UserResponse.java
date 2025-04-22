package com.furniture.api.dto.response;

import com.furniture.core.model.User.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
  private Long id;
  private String email;
  private String firstName;
  private String lastName;
  private String phone;
  private String avatar;
  private Role role;
}
