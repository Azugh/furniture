package com.furniture.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.furniture.api.dto.NotificationDTO;
import com.furniture.core.model.Notification;
import com.furniture.core.service.NotificationService;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OWNER')")
public class NotificationController {
  private final NotificationService notificationService;

  @GetMapping
  public ResponseEntity<List<NotificationDTO>> getUserNotifications() {
    List<Notification> notifications = notificationService.getUserNotifications(getCurrentUserId());
    List<NotificationDTO> notificationDTOs = notifications.stream()
        .map(this::convertToNotificationDTO)
        .toList();
    return ResponseEntity.ok(notificationDTOs);
  }

  @PutMapping("/{id}/read")
  public ResponseEntity<Void> markNotificationAsRead(@PathVariable Long id) {
    notificationService.markAsRead(id);
    return ResponseEntity.noContent().build();
  }

  private NotificationDTO convertToNotificationDTO(Notification notification) {
    NotificationDTO dto = new NotificationDTO();
    dto.setId(notification.getId());
    dto.setMessage(notification.getMessage());
    dto.setIsRead(notification.getIsRead());
    dto.setCreatedAt(notification.getCreatedAt());
    dto.setOrderId(notification.getOrder() != null ? notification.getOrder().getId() : null);
    return dto;
  }

  private Long getCurrentUserId() {
    // Implementation to get current user ID from security context
    return 1L; // Simplified for example
  }
}
