package com.furniture.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.furniture.core.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
