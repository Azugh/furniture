package com.furniture.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.furniture.api.dto.OrderDTO;
import com.furniture.api.dto.OrderHistoryDTO;
import com.furniture.api.dto.OrderItemDTO;
import com.furniture.core.enums.OrderStatus;
import com.furniture.core.model.Order;
import com.furniture.core.model.OrderHistory;
import com.furniture.core.model.OrderItem;
import com.furniture.core.service.OrderService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
  private final OrderService orderService;

  @GetMapping
  @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OWNER')")
  public ResponseEntity<Page<OrderDTO>> getUserOrders(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "15") int size) {
    Page<Order> orders = orderService.getUserOrders(getCurrentUserId(), page, size);
    Page<OrderDTO> dtoPage = orders.map(this::convertToOrderDTO);
    return ResponseEntity.ok(dtoPage);
  }

  @GetMapping("/all")
  @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OWNER')")
  public ResponseEntity<Page<OrderDTO>> getAllActiveOrders(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "15") int size) {
    Page<Order> orders = orderService.getAllActiveOrders(page, size);
    Page<OrderDTO> dtoPage = orders.map(this::convertToOrderDTO);
    return ResponseEntity.ok(dtoPage);
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OWNER')")
  public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
    Order order = orderService.getOrderById(id);
    checkOrderAccess(order);
    return ResponseEntity.ok(convertToOrderDTO(order));
  }

  @PostMapping
  @PreAuthorize("hasRole('CUSTOMER')")
  public ResponseEntity<OrderDTO> createOrder(@RequestBody List<OrderItemDTO> items) {
    // Implementation would convert DTOs to entities and create order
    // This is simplified for example
    Order order = new Order();
    // Set order properties from items
    Order createdOrder = orderService.createOrder(order);
    return ResponseEntity.ok(convertToOrderDTO(createdOrder));
  }

  @PutMapping("/{id}/status")
  @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN') or hasRole('OWNER')")
  public ResponseEntity<OrderDTO> updateOrderStatus(
      @PathVariable Long id,
      @RequestParam OrderStatus status,
      @RequestParam(required = false) String comment) {
    Order updatedOrder = orderService.updateOrderStatus(id, status, comment);
    return ResponseEntity.ok(convertToOrderDTO(updatedOrder));
  }

  @GetMapping("/{id}/history")
  @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OWNER')")
  public ResponseEntity<List<OrderHistoryDTO>> getOrderHistory(@PathVariable Long id) {
    Order order = orderService.getOrderById(id);
    checkOrderAccess(order);

    List<OrderHistory> history = orderService.getOrderHistory(id);
    List<OrderHistoryDTO> historyDTOs = history.stream()
        .map(this::convertToOrderHistoryDTO)
        .collect(Collectors.toList());

    return ResponseEntity.ok(historyDTOs);
  }

  private OrderDTO convertToOrderDTO(Order order) {
    if (order == null) {
      return null;
    }
    OrderDTO dto = new OrderDTO();
    dto.setId(order.getId());
    dto.setOrderDate(order.getOrderDate());
    dto.setTotalAmount(order.getTotalAmount());
    dto.setStatus(order.getStatus());
    dto.setManagerComment(order.getManagerComment());
    dto.setUserId(order.getUser().getId());
    dto.setShippingAddressId(order.getShippingAddress().getId());

    List<OrderItemDTO> itemDTOs = order.getItems().stream()
        .map(this::convertToOrderItemDTO)
        .collect(Collectors.toList());
    dto.setItems(itemDTOs);
    return dto;
  }

  private OrderItemDTO convertToOrderItemDTO(OrderItem item) {
    if (item == null) {
      return null;
    }
    OrderItemDTO dto = new OrderItemDTO();
    dto.setId(item.getId());
    dto.setFurnitureId(item.getFurniture().getId());
    dto.setQuantity(item.getQuantity());
    dto.setPrice(item.getPrice());
    return dto;
  }

  private OrderHistoryDTO convertToOrderHistoryDTO(OrderHistory history) {
    if (history == null) {
      return null;
    }
    OrderHistoryDTO dto = new OrderHistoryDTO();
    dto.setId(history.getId());
    dto.setStatus(history.getStatus());
    dto.setChangedAt(history.getChangedAt());
    dto.setComment(history.getComment());
    if (history.getChangedBy() != null) {
      dto.setChangedById(history.getChangedBy().getId());
      dto.setChangedByName(
          history.getChangedBy().getFirstName() + " " +
              history.getChangedBy().getLastName());
    }

    return dto;
  }

  private Long getCurrentUserId() {
    // Implementation to get current user ID from security context
    // This would typically come from UserDetailsImpl or similar
    return 1L; // Simplified for example
  }

  private void checkOrderAccess(Order order) {
    Long currentUserId = getCurrentUserId();
    if (!order.getUser().getId().equals(currentUserId) &&
        !hasManagerOrAdminRole()) {
      throw new RuntimeException("Access denied");
    }
  }

  private boolean hasManagerOrAdminRole() {
    // Check if current user has manager or admin role
    // Implementation would check security context
    return true; // Simplified for example
  }
}
