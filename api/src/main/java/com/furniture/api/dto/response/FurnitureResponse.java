package com.furniture.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FurnitureResponse {
  private Long id;
  private String name;
  private String description;
  private Double price;
  private Integer stockQuantity;
  private List<String> images;
  private CategoryResponse category;
}
