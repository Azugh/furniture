package com.furniture.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddressDTO {
  private Long id;

  @NotBlank
  private String country;

  @NotBlank
  private String city;

  @NotBlank
  private String street;

  @NotBlank
  private String building;

  private String apartment;

  @NotBlank
  private String postalCode;
}
