package com.furniture.core.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.furniture.core.enums.OrderStatus;
import com.furniture.core.model.Order;
import com.furniture.core.model.OrderHistory;

public interface OrderService {
  Order createOrder(Order order);

  Order updateOrderStatus(Long orderId, OrderStatus status, String comment);

  Order getOrderById(Long orderId);

  Page<Order> getUserOrders(Long userId, int page, int size);

  Page<Order> getAllActiveOrders(int page, int size);

  // List<OrderHistory> getOrderHistory(Long orderId);
}
