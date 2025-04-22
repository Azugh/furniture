package com.furniture.core.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ShippingAddress {

  private String country;
  private String city;
  private String street;
  private String building;
  private String apartment;
  private String postalCode;
}
