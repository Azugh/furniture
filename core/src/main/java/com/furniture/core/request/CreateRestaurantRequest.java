package com.furniture.core.request;

import java.time.LocalDateTime;
import java.util.List;

import com.furniture.core.model.Address;
import com.furniture.core.model.ContactInforamation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRestaurantRequest {

  private Long id;
  private String name;
  private String description;
  private String cuisineType;
  private Address address;
  private ContactInforamation contactInformation;
  private String openingHours;
  private List<String> images;
  private LocalDateTime registrationDate;
}
