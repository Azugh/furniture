package com.furniture.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.furniture.core.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

  List<Notification> findByCustomerId(Long userId);

  List<Notification> findByRestaurantId(Long restaurantId);
}
