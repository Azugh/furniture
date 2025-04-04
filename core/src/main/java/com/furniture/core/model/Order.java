package com.furniture.core.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private User customer;

  @JsonIgnore
  @ManyToOne
  private Restaurant restaurant;

  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;

  @ManyToOne
  private Address deliveryAddress;

  @OneToMany
  private List<OrderItem> items;

  @OneToOne
  private Payment payment;

  private Long totalAmount;
  private String orderStatus;
  private int totalItem;
  private int totalPrice;
}
