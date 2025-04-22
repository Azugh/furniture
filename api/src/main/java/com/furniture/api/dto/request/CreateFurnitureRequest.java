package com.furniture.api.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateFurnitureRequest {

  @NotBlank(message = "Name is required")
  private String name;

  @NotBlank(message = "Description is required")
  private String description;

  @NotNull(message = "Price is required")
  @Positive(message = "Price must be positive")
  private Double price;

  @NotNull(message = "Stock quantity is required")
  @PositiveOrZero(message = "Stock quantity must be positive or zero")
  private Integer stockQuantity;

  @NotNull(message = "Category ID is required")
  private Long categoryId;

  @Size(min = 1, max = 5, message = "There should be between 1 and 5 images")
  private List<String> images;
}
