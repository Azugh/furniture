package com.furniture.core.model;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//TODO FOOD
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Food {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Category category;

  @ElementCollection
  @Column(length = 1000)
  private List<String> images;

  @ManyToOne
  private Restaurant restaurant;

  @ManyToMany
  private List<IngredientItem> ingredients = new ArrayList<>();

  @Temporal(TemporalType.TIMESTAMP)
  private Date creationDate;

  private String name;
  private String description;
  private Long price;
  private boolean available;
  private boolean Vegetarian;
  private boolean Seasonal;

}
