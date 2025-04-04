package com.furniture.core.model;

import java.util.List;

import jakarta.annotation.sql.DataSourceDefinitions;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // TODO FOOD
  @ManyToOne
  private Food food;

  private int quantity;
  private Long totalPrice;

  private List<String> ingredients;
}
