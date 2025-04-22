package com.furniture.api.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.furniture.core.enums.OrderStatus;

import lombok.Data;

@Data
public class OrderDTO {
  private Long id;
  private LocalDateTime orderDate;
  private Double totalAmount;
  private OrderStatus status;
  private String managerComment;
  private Long userId;
  private Long shippingAddressId;
  private List<OrderItemDTO> items;
}
