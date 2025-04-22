package com.furniture.core.service;

import java.util.List;

import com.furniture.core.model.Notification;

public interface NotificationService {
  List<Notification> getUserNotifications(Long userId);

  void markAsRead(Long notificationId);

  void createOrderStatusNotification(Long orderId, String message);
}
