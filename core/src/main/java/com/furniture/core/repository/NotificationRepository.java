package com.furniture.core.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.furniture.core.model.Notification;
import com.furniture.core.model.User;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
  Page<Notification> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);

  List<Notification> findByUserAndIsReadFalse(User user);

  long countByUserAndIsReadFalse(User user);
}
