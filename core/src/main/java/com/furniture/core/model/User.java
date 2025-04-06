package com.furniture.core.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.furniture.core.domain.UserRole;
import com.furniture.core.model.Order; // Убедитесь, что импортированы все необходимые классы
import com.furniture.core.model.RestaurantDto;
import com.furniture.core.model.Address;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @JsonIgnore
  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
  private List<Order> orders = new ArrayList<>();

  @ElementCollection
  private List<RestaurantDto> favorites = new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Address> addresses = new ArrayList<>();

  private String status;
  private String fullName;
  private String email;
  private String password;
  private UserRole role;
}
