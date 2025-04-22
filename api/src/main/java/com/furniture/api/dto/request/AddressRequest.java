package com.furniture.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {

  @NotBlank(message = "Country is required")
  private String country;

  @NotBlank(message = "City is required")
  private String city;

  @NotBlank(message = "Street is required")
  private String street;

  @NotBlank(message = "Building is required")
  private String building;

  private String apartment;

  @NotBlank(message = "Postal code is required")
  private String postalCode;
}
