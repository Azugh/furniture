package com.furniture.core.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.furniture.core.enums.OrderStatus;
import com.furniture.core.model.Furniture;
import com.furniture.core.model.Order;
import com.furniture.core.model.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
  Page<Order> findByUser(User user, Pageable pageable);

  @Query("SELECT o FROM Order o WHERE o.user = :user AND o.status <> 'DELIVERED'")
  Page<Order> findActiveOrdersByUser(User user, Pageable pageable);

  List<Order> findByStatus(OrderStatus status);

  @Query("SELECT o FROM Order o WHERE o.status <> 'DELIVERED'")
  Page<Order> findAllActiveOrders(Pageable pageable);
}
