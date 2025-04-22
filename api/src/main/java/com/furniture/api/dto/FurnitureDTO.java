package com.furniture.api.dto;

import java.util.List;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class FurnitureDTO {
  private Long id;

  @NotBlank
  private String name;

  @NotBlank
  private String description;

  @NotNull
  @Positive
  private Double price;

  @NotNull
  @Positive
  private Integer stockQuantity;

  @NonNull
  private List<String> images;

  @NotNull
  private Long categoryId;
}
