package com.furniture.api.dto;

import java.time.LocalDateTime;

import com.furniture.core.model.User;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewDTO {
  private Long id;

  @NotBlank
  private String comment;

  @Min(1)
  @Max(5)
  private Integer rating;

  @NotNull
  private Long furnitureId;

  private User user;

}
