package com.furniture.api.response;

import com.furniture.core.domain.UserRole;

import lombok.Data;

@Data
public class AuthResponse {

  private String message;
  private String jwt;
  private UserRole role;
}
