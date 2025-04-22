package com.furniture.core.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "furniture")
public class Furniture {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String description;

  @Column(nullable = false)
  private Double price;

  @Column(nullable = false)
  private Integer stockQuantity;

  @ElementCollection
  @CollectionTable(name = "furniture_images", joinColumns = @JoinColumn(name = "furniture_id"))
  @Column(name = "image_url")
  private List<String> images;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;

  @OneToMany(mappedBy = "furniture", cascade = CascadeType.ALL)
  private List<Review> reviews;

  @OneToMany(mappedBy = "furniture", cascade = CascadeType.ALL)
  private List<CartItem> cartItems;

  @OneToMany(mappedBy = "furniture", cascade = CascadeType.ALL)
  private List<OrderItem> orderItems;
}
