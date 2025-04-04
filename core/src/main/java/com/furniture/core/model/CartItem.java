package com.furniture.core.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class CartItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JsonIgnore
  private Cart cart;

  // TODO Сделать furniture
  @ManyToOne
  private Food food;

  private int quantity;
  // TODO удалить ingredients
  private List<String> ingredients;
  private Long totalPrice;

}
