package com.furniture.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse {
  private Long id; // ID товара в корзине
  private Long foodId; // ID продукта (например, мебели или еды)
  private String foodName; // Название продукта
  private int quantity; // Количество товара
  private BigDecimal price; // Цена за единицу товара
  private BigDecimal totalPrice; // Общая стоимость товара в корзине
}
