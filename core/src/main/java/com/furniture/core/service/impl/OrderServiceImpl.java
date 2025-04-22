package com.furniture.core.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.furniture.core.enums.OrderStatus;
import com.furniture.core.model.Cart;
import com.furniture.core.model.Furniture;
import com.furniture.core.model.Order;
import com.furniture.core.model.OrderHistory;
import com.furniture.core.model.OrderItem;
import com.furniture.core.model.User;
import com.furniture.core.repository.CartItemRepository;
import com.furniture.core.repository.CartRepository;
import com.furniture.core.repository.FurnitureRepository;
import com.furniture.core.repository.OrderHistoryRepository;
import com.furniture.core.repository.OrderItemRepository;
import com.furniture.core.repository.OrderRepository;
import com.furniture.core.repository.UserRepository;
import com.furniture.core.service.NotificationService;
import com.furniture.core.service.OrderService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {
  private final OrderRepository orderRepository;
  private final OrderHistoryRepository orderHistoryRepository;
  private final OrderItemRepository orderItemRepository;
  private final UserRepository userRepository;
  private final FurnitureRepository furnitureRepository;
  private final CartRepository cartRepository;
  private final CartItemRepository cartItemRepository;
  private final NotificationService notificationService;

  @Override
  public Order createOrder(Order order) {
    // Validate user
    User user = userRepository.findById(order.getUser().getId())
        .orElseThrow(() -> new RuntimeException("User not found"));

    // Validate address
    if (!user.getAddresses().contains(order.getShippingAddress())) {
      throw new RuntimeException("Invalid shipping address for user");
    }

    // Set order details
    order.setOrderDate(LocalDateTime.now());
    order.setStatus(OrderStatus.PENDING);

    // Calculate total amount and validate stock
    double total = 0;
    for (OrderItem item : order.getItems()) {
      Furniture furniture = furnitureRepository.findById(item.getFurniture().getId())
          .orElseThrow(() -> new RuntimeException("Furniture not found"));

      if (furniture.getStockQuantity() < item.getQuantity()) {
        throw new RuntimeException("Not enough stock for " + furniture.getName());
      }

      item.setPrice(furniture.getPrice());
      total += item.getPrice() * item.getQuantity();

      // Update stock
      furniture.setStockQuantity(furniture.getStockQuantity() - item.getQuantity());
      furnitureRepository.save(furniture);
    }

    order.setTotalAmount(total);

    // Save order
    Order savedOrder = orderRepository.save(order);

    // Save order items
    for (OrderItem item : order.getItems()) {
      item.setOrder(savedOrder);
      orderItemRepository.save(item);
    }

    // Create initial history
    OrderHistory history = new OrderHistory();
    history.setOrder(savedOrder);
    history.setStatus(OrderStatus.PENDING);
    history.setChangedAt(LocalDateTime.now());
    history.setChangedBy(user);
    history.setComment("Order created");
    orderHistoryRepository.save(history);

    // Clear user's cart
    Cart cart = cartRepository.findByUser(user)
        .orElseThrow(() -> new RuntimeException("Cart not found"));
    cartItemRepository.deleteAllByCart(cart);

    // Send notification
    notificationService.createOrderStatusNotification(
        savedOrder.getId(),
        "Your order #" + savedOrder.getId() + " has been placed. Status: PENDING");

    return savedOrder;
  }

  @Override
  public Order updateOrderStatus(Long orderId, OrderStatus status, String comment) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new RuntimeException("Order not found"));

    // Only allow managers to update status
    // This check should be done at controller level with @PreAuthorize

    order.setStatus(status);
    if (comment != null && !comment.isEmpty()) {
      order.setManagerComment(comment);
    }

    Order updatedOrder = orderRepository.save(order);

    // Add to history
    OrderHistory history = new OrderHistory();
    history.setOrder(updatedOrder);
    history.setStatus(status);
    history.setChangedAt(LocalDateTime.now());
    history.setComment(comment != null ? comment : "Status changed to " + status);
    orderHistoryRepository.save(history);

    // Send notification
    notificationService.createOrderStatusNotification(
        orderId,
        "Your order #" + orderId + " status has been updated to " + status +
            (comment != null ? ". Manager comment: " + comment : ""));

    return updatedOrder;
  }

  @Override
  public Order getOrderById(Long orderId) {
    return orderRepository.findById(orderId)
        .orElseThrow(() -> new RuntimeException("Order not found"));
  }

  @Override
  public Page<Order> getUserOrders(Long userId, int page, int size) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));
    return orderRepository.findActiveByUser(user, PageRequest.of(page, size));
  }

  @Override
  public Page<Order> getAllActiveOrders(int page, int size) {
    throw new UnsupportedOperationException("Unimplemented method 'getAllActiveOrders'");
  }

  // @Override
  // public Page<Order> getAllActiveOrders(int page, int size) {
  // return orderRepository.findAllActiveOrders(PageRequest.of(page, size));
  // }

  // @Override
  // public List<OrderHistory> getOrderHistory(Long orderId) {
  // Order order = orderRepository.findById(orderId)
  // .orElseThrow(() -> new RuntimeException("Order not found"));
  // ();
}
