package com.furniture.api.service.implementation;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.furniture.api.service.NotificationService;
import com.furniture.core.model.Notification;
import com.furniture.core.model.Order;
import com.furniture.core.model.Restaurant;
import com.furniture.core.model.User;
import com.furniture.core.repository.NotificationRepository;

@Service
public class NotificationServiceImplementation implements NotificationService {

  private NotificationRepository notificationRepository;

  public NotificationServiceImplementation(NotificationRepository notificationRepository) {
    this.notificationRepository = notificationRepository;
  }

  @Override
  public Notification sendOrderStatusNotification(Order order) {
    Notification notification = new Notification();
    notification.setMessage("your order is " + order.getOrderStatus() + " order id is - " + order.getId());
    notification.setCustomer(order.getCustomer());
    notification.setSentAt(new Date());

    return notificationRepository.save(notification);
  }

  @Override
  public void sendRestaurantNotification(Restaurant restaurant, String message) {
    // TODO Auto-generated method stub

  }

  @Override
  public void sendPromotionalNotification(User user, String message) {
    // TODO Auto-generated method stub

  }

  @Override
  public List<Notification> findUsersNotification(Long userId) {
    // TODO Auto-generated method stub
    return notificationRepository.findByCustomerId(userId);
  }

}
