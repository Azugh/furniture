package com.furniture.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ingredients_item")
public class IngredientsItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private IngredientCategory category;

  @JsonIgnore
  @ManyToOne
  private Restaurant restaurant;

  private boolean inStoke = true;
  private String name;
}
