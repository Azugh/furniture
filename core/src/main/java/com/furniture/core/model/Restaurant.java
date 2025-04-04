package com.furniture.core.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  private User user;

  @ManyToOne
  @JoinColumn(name = "address_id")
  private Address address;

  @Embedded
  private ContactInforamation contactInforamation;

  @JsonIgnore
  @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Review> reviews = new ArrayList<>();

  @ElementCollection
  @Column(length = 1000)
  private List<String> images;

  @JsonIgnore
  @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Order> orders = new ArrayList<>();

  @JsonIgnore
  @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
  private List<Food> foods = new ArrayList<>();

  private String name;
  private String description;
  private String openingHours;
  private String cuisineType;
  private LocalDateTime registrationDate;
  private boolean open;
  private int numRating;
}
