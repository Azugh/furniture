package com.furniture.api.dto;

import com.furniture.core.enums.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderHistoryDTO {
  private Long id;
  private OrderStatus status;
  private LocalDateTime changedAt;
  private String comment;
  private Long changedById;
  private String changedByName;

  // Конструкторы (можно использовать Lombok @AllArgsConstructor и
  // @NoArgsConstructor)
  public OrderHistoryDTO() {
  }

  public OrderHistoryDTO(Long id, OrderStatus status, LocalDateTime changedAt,
      String comment, Long changedById, String changedByName) {
    this.id = id;
    this.status = status;
    this.changedAt = changedAt;
    this.comment = comment;
    this.changedById = changedById;
    this.changedByName = changedByName;
  }
}
