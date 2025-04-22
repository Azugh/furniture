package com.furniture.core.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.furniture.core.model.Notification;
import com.furniture.core.model.Order;
import com.furniture.core.model.User;
import com.furniture.core.repository.NotificationRepository;
import com.furniture.core.repository.OrderRepository;
import com.furniture.core.repository.UserRepository;
import com.furniture.core.service.NotificationService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
  private final NotificationRepository notificationRepository;
  private final OrderRepository orderRepository;
  private final UserRepository userRepository;

  @Override
  public List<Notification> getUserNotifications(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));
    return notificationRepository.findByUser(user);
  }

  @Override
  public void markAsRead(Long notificationId) {
    Notification notification = notificationRepository.findById(notificationId)
        .orElseThrow(() -> new RuntimeException("Notification not found"));
    notification.setRead(true);
    notificationRepository.save(notification);
  }

  @Override
  public void createOrderStatusNotification(Long orderId, String message) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new RuntimeException("Order not found"));

    Notification notification = new Notification();
    notification.setUser(order.getUser());
    notification.setOrder(order);
    notification.setMessage(message);
    notification.setRead(false);
    notification.setCreatedAt(LocalDateTime.now());

    notificationRepository.save(notification);
  }
}
