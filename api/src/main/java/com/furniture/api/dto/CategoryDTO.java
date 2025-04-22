package com.furniture.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryDTO {
  private Long id;

  @NotBlank
  private String name;

  private String description;
}
