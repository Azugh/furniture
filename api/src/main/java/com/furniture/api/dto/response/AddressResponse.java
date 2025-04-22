package com.furniture.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse {
  private Long id;
  private String country;
  private String city;
  private String street;
  private String building;
  private String apartment;
  private String postalCode;
}
