package com.furniture.core.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ContactInforamation {

  private String email;
  private String mobile;
  private String instagram;
  private String twitter;
}
