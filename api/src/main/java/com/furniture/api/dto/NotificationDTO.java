package com.furniture.api.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class NotificationDTO {
  private Long id;
  private String message;
  private Boolean isRead;
  private LocalDateTime createdAt;
  private Long orderId;
}
