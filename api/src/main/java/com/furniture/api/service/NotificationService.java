package com.furniture.api.service;

import java.util.List;

import com.furniture.core.model.Notification;
import com.furniture.core.model.Order;
import com.furniture.core.model.Restaurant;
import com.furniture.core.model.User;

public interface NotificationService {

  public Notification sendOrderStatusNotification(Order order);

  public void sendRestaurantNotification(Restaurant restaurant, String message);

  public void sendPromotionalNotification(User user, String message);

  public List<Notification> findUsersNotification(Long userId);

}
